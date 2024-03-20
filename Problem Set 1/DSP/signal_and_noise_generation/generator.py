import math
import matplotlib.pyplot as plt
from random import random
class Generator:

    def __init__(self, A, t1, d, T, kw, ts, p, f, function):
        self.A = A          # Amplituda
        self.t1 = t1        # Czas początkowy
        self.d = d          # Czas trwania sygnału
        self.T = T          # Okres podstawowy
        self.kw = kw        # Współczynnik wypełnienia
        self.ts = ts        # Skok czasowy
        self.p = p          # Prawdopodobieństwo wystąpienia wartości A (szum impulsowy)
        self.f = f          # Częstotliwość próbkowania
        self.function = function

    def generate_plot(self, is_scatter = False):
        num_of_samples = int(self.d * self.f)

        times = [t / self.f for t in range(num_of_samples)]
        values = [self.function(self, t) for t in times]

        plt.figure(figsize=(8, 4))
        if is_scatter:
            plt.scatter(times, values)
        else:
            plt.plot(times, values)
        plt.xlabel('Time (s)')
        plt.ylabel('Value')
        plt.title('Title')
        plt.grid(True)
        plt.show()

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