import matplotlib.pyplot as plt
from io import BytesIO
import base64
from scipy.integrate import quad
import math

class Operator:
    def __init__(self, generator1, operation, generator2):
        self.generator1 = generator1
        self.generator2 = generator2
        if operation == "+":
            self.function = lambda t: self.generator1.function(self.generator1, t) \
                                      + self.generator2.function(self.generator2, t)
        elif operation == "-":
            self.function = lambda t: self.generator1.function(self.generator1, t) \
                                      - self.generator2.function(self.generator2, t)
        elif operation == "*":
            self.function = lambda t: self.generator1.function(self.generator1, t) \
                                      * self.generator2.function(self.generator2, t)
        elif operation == "/":
            self.function = lambda t: self.generator1.function(self.generator1, t) \
                                      / self.generator2.function(self.generator2, t)


    def generate_plot(self):
        num_of_samples = int(self.generator1.d * self.generator1.f)
        times = [(t / self.generator1.f) for t in range(num_of_samples)]
        times_for_plot = [(t / self.generator1.f) + self.generator1.t1 for t in range(num_of_samples)]
        values = [self.function(t) for t in times]

        fig, axes = plt.subplots(2, 1, figsize=(8, 8))

        #print("Is scatter:", self.is_scatter)
        #if self.is_scatter:
        #    axes[0].scatter(times_for_plot, values)
        #else:
        axes[0].plot(times_for_plot, values)
        axes[0].set_xlabel('Time (s)')
        axes[0].set_ylabel('Value')
        axes[0].set_title('Title')
        axes[0].grid(True)

        axes[1].hist(values, bins=10, edgecolor="black")  # Możesz dostosować liczbę kubełków (bins) według potrzeb
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

    def calculate_mean_value(self, t1, t2):
        fct = lambda x: self.function(x)
        integral, _ = quad(fct, t1, t2)
        integral = (1/(t2-t1))*integral
        return integral

    def calculate_mean_abs_value(self, t1, t2):
        fct = lambda x: abs(self.function(x))
        integral, _ = quad(fct, t1, t2)
        integral = (1/(t2-t1))*integral
        return integral

    def calculate_mean_power(self, t1, t2):
        fct = lambda x: self.function(x) ** 2
        integral, _ = quad(fct, t1, t2)
        integral = (1/(t2-t1))*integral
        return integral

    def calculate_variance(self, t1, t2):
        mean_value = self.calculate_mean_value(t1, t2)
        fct = lambda x: (self.function(x) - mean_value) ** 2
        integral, _ = quad(fct, t1, t2)
        integral = (1 / (t2 - t1)) * integral
        return integral

    def calculate_effective_value(self, t1, t2):
        return math.sqrt(self.calculate_mean_power(t1, t2))
