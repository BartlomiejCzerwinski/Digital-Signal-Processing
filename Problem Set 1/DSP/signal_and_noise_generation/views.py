from django.shortcuts import render
from . import forms

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
