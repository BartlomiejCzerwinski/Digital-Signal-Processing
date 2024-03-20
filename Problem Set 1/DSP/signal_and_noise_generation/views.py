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
        g = generator.Generator(amplitude, start_time, duration, T, kw,
                                jump_time, p, f, fct)
        plot = g.generate_plot()

        return render(request, "signal_and_noise_generation/plot.html",
                      {'plot': plot})

