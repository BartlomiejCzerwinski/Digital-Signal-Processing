import base64
from io import BytesIO

import numpy as np
import matplotlib.pyplot as plt

def generate_plot(values1,values2):
    y = np.convolve(values1, values2)
    plt.stem(y, use_line_collection=True)
    plt.title('Splot y[n] = x[n] * h[n]')
    plt.xlabel('n')
    plt.ylabel('y[n]')
    plt.grid(True)

    plt.tight_layout()

    image_stream = BytesIO()
    plt.savefig(image_stream, format='png')
    image_stream.seek(0)
    image_base64 = base64.b64encode(image_stream.read()).decode('utf-8')
    plt.close()
    return image_base64