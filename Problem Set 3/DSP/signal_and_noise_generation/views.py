import struct
import time

from django.http import HttpResponse, FileResponse
from django.shortcuts import render
from . import forms
from . import generator
from . import convolution
from . import operator
from . import filter as filters
from . import correlation as correlations
import numpy as np

SAVE_T1 = 0
SAVE_F = 1
SAVE_VALUES = []

def index(request):
    form = forms.Form_add()
    return render(request, "signal_and_noise_generation/index.html",
                  {'form': form})

def operation(request):
    form = forms.Form_operation()
    return render(request, "signal_and_noise_generation/operation.html",
                  {'form': form})

def filter(request):
    form = forms.Form_filter()
    return render(request, "signal_and_noise_generation/filter.html",
                  {'form': form})

def correlation(request):
    form = forms.Form_operation()
    return render(request, "signal_and_noise_generation/correlation.html",
                  {'form': form})

def distance(request):
    form = forms.Form_distance()
    return render(request, "signal_and_noise_generation/distance.html",
                  {'form': form})

import numpy as np
from django.shortcuts import render
import matplotlib.pyplot as plt
from io import BytesIO
import base64

def generate_signal(frequency, amplitude, phase, length, sampling_rate):
    t = np.arange(0, length, 1/sampling_rate)
    signal = amplitude * np.sin(2 * np.pi * frequency * t + phase)
    return t, signal

def calculate_correlation_distance(signal1, signal2, sampling_rate, speed):
    correlation = np.correlate(signal1, signal2, mode='full')
    max_corr_index = np.argmax(correlation)
    lag = max_corr_index - len(signal1) + 1
    time_delay = lag / sampling_rate
    distance = time_delay * speed / 2
    return distance

def generate_plot(signal1, signal2, times, calculated_distances, real_distances):
    fig, axs = plt.subplots(3, 1, figsize=(12, 8))

    axs[0].plot(signal1, label='Sygnał wysłany')
    axs[0].legend()
    axs[0].set_title('Sygnał wysłany')
    axs[0].set_xlabel('Czas [s]')
    axs[0].set_ylabel('Amplituda')

    axs[1].plot(signal2, label='Sygnał odbity')
    axs[1].legend()
    axs[1].set_title('Sygnał odbity')
    axs[1].set_xlabel('Czas [s]')
    axs[1].set_ylabel('Amplituda')

    axs[2].plot(times, calculated_distances, marker='o', linestyle='-', color='r', label='Obliczona odległość')
    axs[2].plot(times, real_distances, marker='x', linestyle='--', color='b', label='Rzeczywista odległość')
    axs[2].legend()
    axs[2].set_title('Odległość w czasie')
    axs[2].set_xlabel('Czas [s]')
    axs[2].set_ylabel('Odległość [m]')

    plt.tight_layout()

    image_stream = BytesIO()
    plt.savefig(image_stream, format='png')
    image_stream.seek(0)
    image_base64 = base64.b64encode(image_stream.read()).decode('utf-8')
    plt.close()

    return image_base64


def calculate_distance(request):
    if request.method == 'POST':
        number_of_measures = int(request.POST['number_of_measures'])
        time_unit = float(request.POST['time_unit'])
        real_speed = float(request.POST['real_speed'])
        speed_inside = float(request.POST['speed_inside'])
        T = float(request.POST['T'])
        f = float(request.POST['f'])
        buffer = int(request.POST['buffer'])
        report = float(request.POST['report'])

        sampling_rate = 1 / time_unit  # Przeliczenie jednostki czasowej na częstotliwość próbkowania
        length = buffer / sampling_rate  # Długość sygnału w sekundach
        t, sent_signal = generate_signal(f, 1, 0, length, sampling_rate)

        time_delay = (2 * real_speed) / speed_inside  # Czas opóźnienia
        samples_delay = int(time_delay * sampling_rate)  # Opóźnienie w próbkach

        # Generowanie sygnału zwrotnego z opóźnieniem
        received_signal = np.zeros_like(sent_signal)
        if samples_delay < len(sent_signal):
            received_signal[samples_delay:] = sent_signal[:-samples_delay]

        estimated_distance = calculate_correlation_distance(sent_signal, received_signal, sampling_rate, speed_inside)

        # Wyświetlanie odległości w regularnych odstępach czasowych
        times = np.arange(0, number_of_measures * time_unit, time_unit)
        calculated_distances = []
        real_distances = [real_speed * time for time in times]
        for time in times:
            idx = int(time * sampling_rate)
            if idx <= len(sent_signal) and idx > 0:
                sub_sent_signal = sent_signal[:idx]
                sub_received_signal = received_signal[:idx]
                if len(sub_sent_signal) > 0 and len(sub_received_signal) > 0:
                    distance = calculate_correlation_distance(sub_sent_signal, sub_received_signal, sampling_rate, speed_inside)
                    calculated_distances.append(abs(distance))
                    print(f"DYSTANS (czas {time} s): {distance}")
                else:
                    calculated_distances.append(None)
                    print(f"DYSTANS (czas {time} s): N/A - brak wystarczającej długości sygnału")
            else:
                calculated_distances.append(None)
                print(f"DYSTANS (czas {time} s): N/A - brak wystarczającej długości sygnału")

        plot = generate_plot(sent_signal, received_signal, times, calculated_distances, real_distances)
        return render(request, "signal_and_noise_generation/plot.html", {'plot': plot})

    return render(request, "simulate.html")

def filter_operation(request):
    if request.method == 'POST':
        print(request.POST)
        fct1 = request.POST['function1']
        amplitude1 = float(request.POST['amplitude1'])
        start_time1 = float(request.POST['start_time1'])
        duration1 = float(request.POST['duration1'])
        T1 = float(request.POST['T1'])
        kw1 = float(request.POST['kw1'])
        jump_time1 = float(request.POST['jump_time1'])
        p1 = float(request.POST['p1'])
        f1 = int(request.POST['f1'])
        filter_level = int(request.POST['filter_level'])
        window = request.POST['window']
        filter = request.POST['filter']
        cutoff = int(request.POST['cutoff_frequency'])

        g1 = generator.Generator(amplitude1, start_time1, duration1,
                                 T1, kw1, jump_time1, p1, f1, fct1, 0 * 5)
        values, times = g1.get_values_and_times()

        if filter == "dolnoprzepustowy":
            if window == "okno prostokątne":
                plot = filters.low_pass_fir_filter_rectangular(values, cutoff, f1, filter_level)
                return render(request, "signal_and_noise_generation/plot.html",
                              {'plot': plot})
            else:
                print("AAA")
                plot = filters.low_pass_fir_filter_hamming(values, cutoff, f1, filter_level)
                return render(request, "signal_and_noise_generation/plot.html",
                              {'plot': plot})
        else:
            if window == "okno prostokątne":
                plot = filters.high_pass_fir_filter_rectangular(values, cutoff, f1, filter_level)
                return render(request, "signal_and_noise_generation/plot.html",
                              {'plot': plot})
            else:
                plot = filters.high_pass_fir_filter_hamming(values, cutoff, f1, filter_level)
                return render(request, "signal_and_noise_generation/plot.html",
                              {'plot': plot})

def calculate_correlation(request):
    if request.method == 'POST':
        print(request.POST)
        fct1 = request.POST['function1']
        amplitude1 = float(request.POST['amplitude1'])
        start_time1 = float(request.POST['start_time1'])
        duration1 = float(request.POST['duration1'])
        T1 = float(request.POST['T1'])
        kw1 = float(request.POST['kw1'])
        jump_time1 = float(request.POST['jump_time1'])
        p1 = float(request.POST['p1'])
        f1 = int(request.POST['f1'])
        fct2 = request.POST['function2']
        amplitude2 = float(request.POST['amplitude2'])
        start_time2 = float(request.POST['start_time2'])
        duration2 = float(request.POST['duration2'])
        T2 = float(request.POST['T2'])
        kw2 = float(request.POST['kw2'])
        jump_time2 = float(request.POST['jump_time2'])
        p2 = float(request.POST['p2'])
        f2= int(request.POST['f2'])
        g1 = generator.Generator(amplitude1, start_time1, duration1,
                                 T1, kw1, jump_time1, p1, f1, fct1, 0*5)
        g2 = generator.Generator(amplitude2, start_time2, duration2,
                                 T2, kw2, jump_time2, p2, f2, fct2, 0*5)

        plot = correlations.generate_plot(g1.get_values_and_times()[0], g2.get_values_and_times()[0])
        return render(request, "signal_and_noise_generation/plot.html",
                      {'plot': plot})

def calculate_operation(request):
    if request.method == 'POST':
        print(request.POST)
        fct1 = request.POST['function1']
        amplitude1 = float(request.POST['amplitude1'])
        start_time1 = float(request.POST['start_time1'])
        duration1 = float(request.POST['duration1'])
        T1 = float(request.POST['T1'])
        kw1 = float(request.POST['kw1'])
        jump_time1 = float(request.POST['jump_time1'])
        p1 = float(request.POST['p1'])
        f1 = int(request.POST['f1'])
        fct2 = request.POST['function2']
        amplitude2 = float(request.POST['amplitude2'])
        start_time2 = float(request.POST['start_time2'])
        duration2 = float(request.POST['duration2'])
        T2 = float(request.POST['T2'])
        kw2 = float(request.POST['kw2'])
        jump_time2 = float(request.POST['jump_time2'])
        p2 = float(request.POST['p2'])
        f2= int(request.POST['f2'])
        g1 = generator.Generator(amplitude1, start_time1, duration1,
                                 T1, kw1, jump_time1, p1, f1, fct1, 0*5)
        g2 = generator.Generator(amplitude2, start_time2, duration2,
                                 T2, kw2, jump_time2, p2, f2, fct2, 0*5)

        plot = convolution.generate_plot(g1.get_values_and_times()[0], g2.get_values_and_times()[0])
        return render(request, "signal_and_noise_generation/plot.html",
                      {'plot': plot})


def draw_plot(request):
    if request.method == 'POST':
        fct = request.POST['function']
        amplitude = float(request.POST['amplitude'])
        start_time = float(request.POST['start_time'])
        duration = float(request.POST['duration'])
        T = float(request.POST['T'])
        kw = float(request.POST['kw'])
        jump_time = float(request.POST['jump_time'])
        p = float(request.POST['p'])
        f = int(request.POST['f'])
        bins_num = int(request.POST['bins_num'])
        print("bins: ", bins_num)
        g = generator.Generator(amplitude, start_time, duration, T, kw,
                                jump_time, p, f, fct, bins_num)
        plot = g.generate_plot()
        global SAVE_T1, SAVE_F, SAVE_VALUES
        SAVE_T1 = start_time
        SAVE_F = f
        SAVE_VALUES = g.get_values()


        mean_value = g.calculate_mean_value(g.t1, g.t1 + g.d)
        mean_abs_value = g.calculate_mean_abs_value(g.t1, g.t1 + g.d)
        effective_value = g.calculate_effective_value(g.t1, g.t1 + g.d)
        variance = g.calculate_variance(g.t1, g.t1 + g.d)
        mean_power = g.calculate_mean_power(g.t1, g.t1 + g.d)

        return render(request, "signal_and_noise_generation/plot.html",
                      {'plot': plot,
                       'mean_value': mean_value,
                       'mean_abs_value': mean_abs_value,
                       'effective_value': effective_value,
                       'variance': variance,
                       'mean_power': mean_power})

def save_plot(request):
    global SAVE_T1, SAVE_F, SAVE_VALUES
    print("T1:", SAVE_T1)
    print("F:", SAVE_F)
    print("save values:", SAVE_VALUES)
    all_values = [SAVE_T1, SAVE_F] + SAVE_VALUES

    data_format = 'dd' + 'd' * len(SAVE_VALUES)

    binary_data = struct.pack(data_format, *all_values)

    with open('dane.bin', 'wb') as f:
        f.write(binary_data)

    response = FileResponse(open('dane.bin', 'rb'))
    response['Content-Disposition'] = 'attachment; filename="dane.bin"'

    return response

def upload_plot(request):
    form = forms.File_upload_form()
    if request.method == "POST":
        uploaded_file = request.FILES['file']

    return render(request, "signal_and_noise_generation/upload.html",
                  {'form': form})


