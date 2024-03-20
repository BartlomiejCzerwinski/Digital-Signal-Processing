from django.urls import path

from . import views

urlpatterns = [
    path("", views.index, name="index"),
    path('draw_plot', views.draw_plot, name="draw_plot",)
]