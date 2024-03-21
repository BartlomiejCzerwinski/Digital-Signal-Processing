import struct

from django.http import HttpResponse, FileResponse
from django.shortcuts import render
from . import forms
from . import generator
from . import operator

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
        operation = request.POST['operation']
        fct2 = request.POST['function2']
        amplitude2 = float(request.POST['amplitude2'])
        start_time2 = float(request.POST['start_time2'])
        duration2 = float(request.POST['duration2'])
        T2 = float(request.POST['T2'])
        kw2 = float(request.POST['kw2'])
        jump_time2 = float(request.POST['jump_time2'])
        p2 = float(request.POST['p2'])
        f2= int(request.POST['f2'])
        bins_num = int(request.POST['bins_num'])
        g1 = generator.Generator(amplitude1, start_time1, duration1,
                                 T1, kw1, jump_time1, p1, f1, fct1, bins_num*5)
        g2 = generator.Generator(amplitude2, start_time2, duration2,
                                 T2, kw2, jump_time2, p2, f2, fct2, bins_num * 5)
        o = operator.Operator(g1, operation, g2)
        plot = o.generate_plot()

        global SAVE_T1, SAVE_F, SAVE_VALUES
        SAVE_T1 = g1.t1
        SAVE_F = g1.f
        SAVE_VALUES = o.get_values()

        mean_value = o.calculate_mean_value(g1.t1, g1.t1 + g1.d)
        mean_abs_value = o.calculate_mean_abs_value(g1.t1, g1.t1 + g1.d)
        effective_value = o.calculate_effective_value(g1.t1, g1.t1 + g1.d)
        variance = o.calculate_variance(g1.t1, g1.t1 + g1.d)
        mean_power = o.calculate_mean_power(g1.t1, g1.t1 + g1.d)
        return render(request, "signal_and_noise_generation/plot.html",
                      {'plot': plot,
                       'mean_value': mean_value,
                       'mean_abs_value': mean_abs_value,
                       'effective_value': effective_value,
                       'variance': variance,
                       'mean_power': mean_power})

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
        bins_num *= 5
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


