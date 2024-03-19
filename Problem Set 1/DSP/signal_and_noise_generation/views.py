from django.shortcuts import render
from . import forms

def index(request):
    form = forms.Form_add()
    return render(request, "signal_and_noise_generation/index.html",
                  {'form': form})
