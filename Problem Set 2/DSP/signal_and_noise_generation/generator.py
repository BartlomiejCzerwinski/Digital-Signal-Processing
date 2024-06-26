import base64
import math
import matplotlib.pyplot as plt
from random import random
from io import BytesIO
import math
import numpy as np
from scipy.integrate import quad

class Generator:

    def __init__(self, A, t1, d, T, kw, ts, p, f, fq, fsinc, function_name):
        self.A = A          # Amplituda
        self.t1 = t1        # Czas początkowy
        self.d = d          # Czas trwania sygnału
        self.T = T          # Okres podstawowy
        self.kw = kw        # Współczynnik wypełnienia
        self.ts = ts        # Skok czasowy
        self.p = p          # Prawdopodobieństwo wystąpienia wartości A (szum impulsowy)
        self.f = f          # Częstotliwość próbkowania
        self.fq = fq
        self.fsinc = fsinc
        self.function = self.setFunctionByName(function_name)
        self.values = []
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

    # Corrected sinc method
    def sinc(self, x):
        return np.sinc(x)

    def sinc_reconstruction(self, times, quantization_values):
        num_points = int(self.fsinc * self.d)
        reconstruction_times = np.linspace(self.t1, self.t1 + self.d, num_points)
        print("DEBUG: ", reconstruction_times)
        reconstructed_signal = np.zeros(num_points)
        T = times[1] - times[0]  # Zakładając, że czasy próbkowania są równomiernie rozłożone
        print("TTT: ", T)

        for i, t in enumerate(reconstruction_times):
            for n, j in zip(times, quantization_values):
                reconstructed_signal[i] += j * self.sinc((t - n) / T)
        reconstruction_times /= T
        return reconstructed_signal, reconstruction_times


    def mean_sq_error(self, real_values, predicted_values):
        err = 0
        for i in range(len(real_values)):
            if i < len(real_values) and i < len(predicted_values):
                err += (real_values[i] - predicted_values[i])**2
        err /= len(real_values)
        return err

    def SNR(self, real_values, predicted_values):
        real_sum = 0
        for i in real_values:
            real_sum += (i**2)
        mean_sq_err = 0
        for i in range(len(real_values)):
            if i < len(real_values) and i < len(predicted_values):
                mean_sq_err += (real_values[i] - predicted_values[i]) ** 2
        return 10*math.log10(real_sum/mean_sq_err)

    def peak_SNR(self, real_values, predicted_values):
        max_val = max(predicted_values)
        result = max_val/self.mean_sq_error(real_values, predicted_values)
        return 10*math.log10(result)

    def max_difference(self, real_values, predicted_values):
        max_dif = 0
        for i in range(len(real_values)):
            if i < len(real_values) and i < len(predicted_values):
                if abs(real_values[i] - predicted_values[i]) > max_dif:
                    max_dif = abs(real_values[i] - predicted_values[i])
        return max_dif

    def ENOB(self, real_values, predicted_values):
        snr = self.SNR(real_values, predicted_values)
        return (snr - 1.76)/6.02


    def generate_plot(self):
        # Próbkowanie równomierne
        num_of_samples = int(self.d * self.f)
        times = [(t / self.f) for t in range(num_of_samples)]
        times_for_plot = [(t / self.f) + self.t1 for t in range(num_of_samples)]
        values = [self.function(self, t) for t in times]

        # Kwantyzacja równomierna z zaokrągleniem
        quantization_step = int(self.f / self.fq)
        quantization_times = times_for_plot[::quantization_step]
        quantization_values = [round(self.function(self, t), 1) for t in quantization_times]


        fig, axes = plt.subplots(5, 1, figsize=(8, 16))

        # Wykres przed kwantyzacją
        axes[0].plot(times_for_plot, values)
        axes[0].set_xlabel('Czas (s)')
        axes[0].set_ylabel('Amplituda')
        axes[0].set_title('Sygnał przed kwantyzacją')
        axes[0].grid(True)

        # Wykres próbek sygnału kwantyzacji
        axes[1].scatter(quantization_times, quantization_values)
        axes[1].set_xlabel('Czas (s)')
        axes[1].set_ylabel('Amplituda')
        axes[1].set_title('Próbki sygnału do kwantyzacji')
        axes[1].grid(True)

        # Kwantyzacja równomierna z zaokrągleniem
        axes[2].plot(quantization_times, quantization_values, drawstyle='steps-post')
        axes[2].plot(times_for_plot, values)
        axes[2].set_xlabel('Czas (s)')
        axes[2].set_ylabel('Amplituda')
        axes[2].set_title('Kwantyzacja równomierna z zaokrągleniem')
        axes[2].grid(True)

        # Wykres kwantyzacji
        axes[3].plot(quantization_times, quantization_values, drawstyle='steps-post')
        axes[3].set_xlabel('Czas (s)')
        axes[3].set_ylabel('Amplituda')
        axes[3].set_title('Ekstrapolacja zerowego rzędu')
        axes[3].grid(True)
        reconstructed_values, reconstructed_times = self.sinc_reconstruction(quantization_times, quantization_values)
        print(reconstructed_times)
        # Wykres po rekonstrukcji
        axes[4].plot(reconstructed_times/self.fq, reconstructed_values, label='Zrekonstruowany', color='red')
        axes[4].plot(quantization_times, quantization_values, drawstyle='steps-post', alpha=0.2, label='Kwantyzacja')
        axes[4].plot(times_for_plot, values, alpha=0.2, label='Oryginalny')

        # Dodanie legendy do wykresu
        axes[4].legend(loc='upper right')
        axes[4].set_xlabel('Czas (s)')
        axes[4].set_ylabel('Amplituda')
        axes[4].set_title('Rekonstrukcja w oparciu o funkcję sinc')
        axes[4].grid(True)
        print(quantization_values)
        print(reconstructed_values)
        print("Blad sredniokwadratowy", self.mean_sq_error(quantization_values, reconstructed_values))
        #print("SNR: ", self.SNR(quantization_values, reconstructed_values))
        print("PEAK SNR: ", 0)#self.peak_SNR(quantization_values, reconstructed_values))
        print("Max dif: ", 0)#self.max_difference(quantization_values, reconstructed_values))
        print("ENOB: ", 0)#self.ENOB(quantization_values, reconstructed_values))

        plt.tight_layout()

        # Zapisanie wykresu do obiektu BytesIO
        image_stream = BytesIO()
        plt.savefig(image_stream, format='png')
        image_stream.seek(0)
        image_base64 = base64.b64encode(image_stream.read()).decode('utf-8')

        # Obliczenia dodatkowych parametrów
        mean_sq_err = self.mean_sq_error(quantization_values, reconstructed_values)
        snr = self.SNR(quantization_values, reconstructed_values)
        peak_snr = self.peak_SNR(quantization_values, reconstructed_values)
        max_diff = self.max_difference(quantization_values, reconstructed_values)
        enob = self.ENOB(quantization_values, reconstructed_values)

        # Wypisanie dodatkowych parametrów
        print("Blad sredniokwadratowy:", self.mean_sq_error(quantization_values, reconstructed_values))
        #print("SNR:", snr)
        #print("PEAK SNR:", peak_snr)
        #print("Max dif:", max_diff)
        #print("ENOB:", enob)

        extra_params = {
            'MSE': abs(mean_sq_err),
            'SNR': abs(snr),
            'PSNR': abs(peak_snr),
            'MD': abs(max_diff),
            'ENOB': abs(enob)
        }

        return image_base64, extra_params

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

    def get_values(self):
        return self.values