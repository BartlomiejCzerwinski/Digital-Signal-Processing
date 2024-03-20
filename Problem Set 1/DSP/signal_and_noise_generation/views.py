from django.http import HttpResponse
from django.shortcuts import render
from . import forms
from . import generator

def index(request):
    if request.method == "POST":
        fct = request.POST['function']
        amplitude = request.POST['amplitude']
        start_time = request.POST['start_time']
        duration = request.POST['duration']
        T = request.POST['T']
        kw = request.POST['kw']
        jump_time = request.POST['jump_time']
        p = request.POST['p']
        f = request.POST['f']

    form = forms.Form_add()
    return render(request, "signal_and_noise_generation/index.html",
                  {'form': form})

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


