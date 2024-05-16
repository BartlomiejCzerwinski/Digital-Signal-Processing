from django.urls import path

from . import views

urlpatterns = [
    path("", views.index, name="index"),
    path('draw_plot', views.draw_plot, name="draw_plot",),
    path("operation", views.operation, name="operation"),
    path("filter", views.filter, name="filter"),
    path("correlation", views.correlation, name="correlation"),
    path("distance", views.distance, name="distance"),
    path("calculate_distance", views.calculate_distance, name="calculate_distance"),
    path("calculate_operation", views.calculate_operation, name="calculate_operation"),
    path("calculate_correlation", views.calculate_correlation, name="calculate_correlation"),
    path("filter_operation", views.filter_operation, name="filter_operation"),
    path("save_plot", views.save_plot, name="save_plot"),
    path("upload_plot", views.upload_plot, name="upload_plot"),
]