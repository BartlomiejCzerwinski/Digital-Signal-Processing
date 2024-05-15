import base64
from io import BytesIO

import numpy as np
import matplotlib.pyplot as plt

def high_pass_fir_filter_rectangular(signal, cutoff_frequency, sample_rate, filter_order):
    # Obliczenie znormalizowanej częstotliwości odcięcia
    normalized_cutoff = 2 * cutoff_frequency / sample_rate
    n = np.arange(filter_order)

    # Idealna odpowiedź impulsowa filtra dolnoprzepustowego
    h_ideal = np.sinc(normalized_cutoff * (n - (filter_order - 1) / 2))

    # Okno prostokątne
    window_func = np.ones(filter_order)

    # Filtracja sygnału
    h_lp = h_ideal * window_func
    h_lp /= np.sum(h_lp)  # Normalizacja

    # Przekształcenie filtra dolnoprzepustowego w górnoprzepustowy
    h_hp = -h_lp
    h_hp[(filter_order - 1) // 2] += 1  # Dodanie delta impulsu

    filtered_signal = np.convolve(signal, h_hp, mode='same')

    n = np.arange(len(signal))  # Automatycznie generowane wartości x
    plt.scatter(n, filtered_signal)  # c='b' oznacza kolor niebieski
    plt.title('Filtr górnoprzepustowy z oknem prostokątnym')
    plt.xlabel('n')
    plt.ylabel('A')
    plt.grid(True)

    plt.tight_layout()

    image_stream = BytesIO()
    plt.savefig(image_stream, format='png')
    image_stream.seek(0)
    image_base64 = base64.b64encode(image_stream.read()).decode('utf-8')
    plt.close()

    return image_base64


def high_pass_fir_filter_hamming(signal, cutoff_frequency, sample_rate, filter_order):
    # Obliczenie znormalizowanej częstotliwości odcięcia
    normalized_cutoff = 2 * cutoff_frequency / sample_rate
    n = np.arange(filter_order)

    # Idealna odpowiedź impulsowa filtra dolnoprzepustowego
    h_ideal = np.sinc(normalized_cutoff * (n - (filter_order - 1) / 2))

    # Okno Hamminga
    window_func = np.hamming(filter_order)

    # Filtracja sygnału
    h_lp = h_ideal * window_func
    h_lp /= np.sum(h_lp)  # Normalizacja

    # Przekształcenie filtra dolnoprzepustowego w górnoprzepustowy
    h_hp = -h_lp
    h_hp[(filter_order - 1) // 2] += 1  # Dodanie delta impulsu

    filtered_signal = np.convolve(signal, h_hp, mode='same')

    n = np.arange(len(signal))  # Automatycznie generowane wartości x
    plt.scatter(n, filtered_signal)  # c='b' oznacza kolor niebieski
    plt.title('Filtr górnoprzepustowy z oknem Hamminga')
    plt.xlabel('n')
    plt.ylabel('A')
    plt.grid(True)

    plt.tight_layout()

    image_stream = BytesIO()
    plt.savefig(image_stream, format='png')
    image_stream.seek(0)
    image_base64 = base64.b64encode(image_stream.read()).decode('utf-8')
    plt.close()

    return image_base64


def low_pass_fir_filter_rectangular(signal, cutoff_frequency, sample_rate, filter_order):
    # Obliczenie znormalizowanej częstotliwości odcięcia
    normalized_cutoff = 2 * cutoff_frequency / sample_rate
    n = np.arange(filter_order)

    # Idealna odpowiedź impulsowa filtra dolnoprzepustowego
    h_ideal = np.sinc(normalized_cutoff * (n - (filter_order - 1) / 2))

    # Okno prostokątne
    window_func = np.ones(filter_order)

    # Filtracja sygnału
    h = h_ideal * window_func
    h /= np.sum(h)  # Normalizacja

    filtered_signal = np.convolve(signal, h, mode='same')

    n = np.arange(len(signal))  # Automatycznie generowane wartości x
    plt.scatter(n, signal)  # c='b' oznacza kolor niebieski
    plt.title('Filtr dolnoprzepustowy z oknem prostokątnym')
    plt.xlabel('n')
    plt.ylabel('A')
    plt.grid(True)

    plt.tight_layout()

    image_stream = BytesIO()
    plt.savefig(image_stream, format='png')
    image_stream.seek(0)
    image_base64 = base64.b64encode(image_stream.read()).decode('utf-8')
    plt.close()

    return image_base64


def low_pass_fir_filter_hamming(signal, cutoff_frequency, sample_rate, filter_order):
    # Obliczenie znormalizowanej częstotliwości odcięcia
    normalized_cutoff = 2 * cutoff_frequency / sample_rate
    n = np.arange(filter_order)

    # Idealna odpowiedź impulsowa filtra dolnoprzepustowego
    h_ideal = np.sinc(normalized_cutoff * (n - (filter_order - 1) / 2))

    # Okno Hamminga
    window_func = np.hamming(filter_order)

    # Filtracja sygnału
    h = h_ideal * window_func


    filtered_signal = np.convolve(signal, h, mode='same')

    n = np.arange(len(signal))  # Automatycznie generowane wartości x
    plt.scatter(n, filtered_signal)  # c='b' oznacza kolor niebieski
    plt.title('Filtr dolnoprzepustowy z oknem Hamminga')
    plt.xlabel('n')
    plt.ylabel('A')
    plt.grid(True)

    plt.tight_layout()

    image_stream = BytesIO()
    plt.savefig(image_stream, format='png')
    image_stream.seek(0)
    image_base64 = base64.b64encode(image_stream.read()).decode('utf-8')
    plt.close()

    return image_base64
