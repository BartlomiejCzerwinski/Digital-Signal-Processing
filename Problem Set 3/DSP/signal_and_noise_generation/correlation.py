import base64
from io import BytesIO

import numpy as np
import matplotlib.pyplot as plt

import base64
from io import BytesIO

import numpy as np
import matplotlib.pyplot as plt


def generate_plot(signal1, signal2):
    direct_correlation = np.correlate(signal1, signal2, mode='full')


    convolution_correlation = np.convolve(signal1, signal2, mode='full')
    n = np.arange(len(direct_correlation))

    fig, axs = plt.subplots(2, 1, figsize=(8, 6))

    axs[0].plot(n, direct_correlation)
    axs[0].set_title('Korelacja wzajemna sygnałów signal1 i signal2 (implementacja bezpośrednia)')
    axs[0].set_xlabel('n')
    axs[0].set_ylabel('A')
    axs[0].grid(True)

    axs[1].plot(n, convolution_correlation)
    axs[1].set_title('Korelacja wzajemna sygnałów signal1 i signal2 (splot)')
    axs[1].set_xlabel('n')
    axs[1].set_ylabel('A')
    axs[1].grid(True)

    plt.tight_layout()

    image_stream = BytesIO()
    plt.savefig(image_stream, format='png')
    image_stream.seek(0)
    image_base64 = base64.b64encode(image_stream.read()).decode('utf-8')
    plt.close()
    return image_base64
