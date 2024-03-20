import base64
import math
import matplotlib.pyplot as plt
from random import random
from io import BytesIO
import numpy as np
from scipy.integrate import quad
from functools import partial
class Generator:

    def __init__(self, A, t1, d, T, kw, ts, p, f, function_name, bins_num):
        self.A = A          # Amplituda
        self.t1 = t1        # Czas początkowy
        self.d = d          # Czas trwania sygnału
        self.T = T          # Okres podstawowy
        self.kw = kw        # Współczynnik wypełnienia
        self.ts = ts        # Skok czasowy
        self.p = p          # Prawdopodobieństwo wystąpienia wartości A (szum impulsowy)
        self.f = f          # Częstotliwość próbkowania
        self.function = self.setFunctionByName(function_name)
        self.bins_num = bins_num
        if function_name == 'unit_impulse' or function_name == 'impulse_noise':
            self.is_scatter = True
        else:
            self.is_scatter = False

    def calculate_mean_value(self, t1, t2):
        fct = lambda x: self.function(self, x)
        integral, _ = quad(fct, t1, t2)
        integral = (1/(t2-t1))*integral
        return integral

    def calculate_mean_abs_value(self, t1, t2):
        fct = lambda x: abs(self.function(self, x))
        integral, _ = quad(fct, t1, t2)
        integral = (1/(t2-t1))*integral
        return integral

    def calculate_mean_power(self, t1, t2):
        fct = lambda x: self.function(self, x) ** 2
        integral, _ = quad(fct, t1, t2)
        integral = (1/(t2-t1))*integral
        return integral

    def calculate_variance(self, t1, t2):
        mean_value = self.calculate_mean_value(t1, t2)
        fct = lambda x: (self.function(self, x) - mean_value) ** 2
        integral, _ = quad(fct, t1, t2)
        integral = (1 / (t2 - t1)) * integral
        return integral

    def calculate_effective_value(self, t1, t2):
        return math.sqrt(self.calculate_mean_power(t1, t2))


    def generate_plot(self):
        num_of_samples = int(self.d * self.f)
        times = [(t / self.f) for t in range(num_of_samples)]
        times_for_plot = [(t / self.f) + self.t1 for t in range(num_of_samples)]
        values = [self.function(self, t) for t in times]

        fig, axes = plt.subplots(2, 1, figsize=(8, 8))

        print("Is scatter:", self.is_scatter)
        if self.is_scatter:
            axes[0].scatter(times_for_plot, values)
        else:
            axes[0].plot(times_for_plot, values)
        axes[0].set_xlabel('Time (s)')
        axes[0].set_ylabel('Value')
        axes[0].set_title('Title')
        axes[0].grid(True)

        axes[1].hist(values, bins=self.bins_num, edgecolor="black")  # Możesz dostosować liczbę kubełków (bins) według potrzeb
        axes[1].set_xlabel('Value')
        axes[1].set_ylabel('Frequency')
        axes[1].set_title('Histogram of Values')
        axes[1].grid(True)

        plt.tight_layout()

        image_stream = BytesIO()
        plt.savefig(image_stream, format='png')
        image_stream.seek(0)
        image_base64 = base64.b64encode(image_stream.read()).decode('utf-8')

        return image_base64

    # (S1) szum o rozkładzie jednostajnym;
    def uniform_distribution(self, time):
        return (random() * 2 * self.A) - self.A

    #(S2) szum gaussowski;
    def gaussian_noise(self, time):
        std_dev = self.A / 3
        u1 = 1.0 - random()
        u2 = 1.0 - random()
        normal = math.sqrt(-2.0 * math.log(u1)) * math.sin(2.0 * math.pi * u2)

        return normal * std_dev

    #(S3) sygnał sinusoidalny;
    def sinusoidal_signal(self, time):
        return self.A * math.sin((2 * math.pi / self.T) * (time - self.t1))

    #(S4) sygnał sinusoidalny wyprostowany jednopołówkowo;
    def half_wave_rectifier_signal(self, time):
        return 0.5 * self.A * (math.sin((2 * math.pi / self.T) * (time - self.t1)) + math.fabs(math.sin((2 * math.pi / self.T) * (time - self.t1))))


    #(S5) sygnał sinusoidalny wyprostowany dwupołówkowo;
    def full_wave_rectifier_signal(self, time):
        return self.A * math.fabs(math.sin((2 * math.pi / self.T) * (time - self.t1)))

    #(S6) sygnał prostokątny;
    def rectangular_signal(self, time):
        k = int((time / self.T) - (self.t1 / self.T))
        if time >= (k * self.T + self.t1) and time < (self.kw * self.T + k * self.T + self.t1):
            return self.A
        return 0

    #(S7) sygnał prostokątny symetryczny;
    def rectangular_symmetrical_signal(self, time):
        k = int((time / self.T) - (self.t1 / self.T))
        if time >= k * self.T + self.t1 and time < self.kw * self.T + k * self.T + self.t1:
            return self.A
        return -self.A

    #(S8) sygnał trójkątny;
    def triangular_signal(self, time):
        k = int((time / self.T) - (self.t1 / self.T))
        if time >= k * self.T + self.t1 and time < self.kw * self.T + k * self.T + self.t1:
            return (self.A / (self.kw * self.T)) * (time - k * self.T - self.t1)
        return -self.A / (self.T * (1 - self.kw)) * (time - k * self.T - self.t1) + (self.A / (1 - self.kw))

    #(S9) skok jednostkowy;
    def unit_step(self, time):
        if time > self.ts:
            return self.A
        elif time == self.ts:
            return 0.5 * self.A
        else:
            return 0

    #(S10) impuls jednostkowy;
    def unit_impulse(self, time):
        if time > self.ts:
            return self.A
        elif time == self.ts:
            return 0.5 * self.A
        else:
            return 0

    #(S11) szum impulsowy;
    def impulse_noise(self, time):
        random_double = random()
        if self.p > random_double:
            return self.A
        return 0

    def setFunctionByName(self, function_name):
        if function_name == 'uniform_distribution':
            return Generator.uniform_distribution
        elif function_name == 'gaussian_noise':
            return Generator.gaussian_noise
        elif function_name == 'sinusoidal_signal':
            return Generator.sinusoidal_signal
        elif function_name == 'half_wave_rectifier_signal':
            return Generator.half_wave_rectifier_signal
        elif function_name == 'full_wave_rectifier_signal':
            return Generator.full_wave_rectifier_signal
        elif function_name == 'rectangular_signal':
            return Generator.rectangular_signal
        elif function_name == 'rectangular_symmetrical_signal':
            return Generator.rectangular_symmetrical_signal
        elif function_name == 'triangular_signal':
            return Generator.triangular_signal
        elif function_name == 'unit_step':
            return Generator.unit_step
        elif function_name == 'unit_impulse':
            return Generator.unit_impulse
        elif function_name == 'impulse_noise':
            return Generator.impulse_noise