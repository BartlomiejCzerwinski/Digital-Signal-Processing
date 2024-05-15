from django.urls import path

from . import views

urlpatterns = [
    path("", views.index, name="index"),
    path('draw_plot', views.draw_plot, name="draw_plot",),
    path("operation", views.operation, name="operation"),
    path("calculate_operation", views.calculate_operation, name="calculate_operation"),
    path("save_plot", views.save_plot, name="save_plot"),
    path("upload_plot", views.upload_plot, name="upload_plot"),
]