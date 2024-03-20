from django import forms

def is_positive_validator(value):
    if value <= 0:
        raise forms.ValidationError("This field must be greater than 0")

class Form_add(forms.Form):
    amplitude = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Amplituda (A)'}), label='', validators=[is_positive_validator])
    start_time = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas początkowy (t1)'}), label='', validators=[is_positive_validator])
    duration = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas trwania sygnału(d)'}), label='', validators=[is_positive_validator])
    T = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Okres podstawowy (T)'}), label='', validators=[is_positive_validator])
    kw = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Współczynnik wypełnienia (kw)'}),
                                label='', validators=[is_positive_validator])
    jump_time = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas skoku (ts)'}),
                                label='', validators=[is_positive_validator])
    p = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Prawdopodobieństwo (p)'}),
                                label='', validators=[is_positive_validator])
    f = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Częstotliwosc próbkowania'}),
                                label='', validators=[is_positive_validator])
    dropdown_choices = [
        ('uniform_distribution', 'Szum o rozkładzie jednostajnym'),
        ('gaussian_noise', 'Szum Gaussowski'),
        ('sinusoidal_signal', 'Sygnał sinusoidalny'),
        ('half_wave_rectifier_signal', 'Sygnał sinusoidalny wyprostowany jednopołówkowo'),
        ('full_wave_rectifier_signal', 'Sygnał sinusoidalny wyprostowany dwupołówkowo'),
        ('rectangular_signal', 'Sygnał prostokątny'),
        ('rectangular_symmetrical_signal', 'Sygnał prostokątny symetryczny'),
        ('triangular_signal', 'Sygnał trójkątny'),
        ('unit_step', 'Skok jednostkowy'),
        ('unit_impulse', 'Impuls jednostkowy'),
        ('impulse_noise', 'Szum impulsowy'),

    ]
    function = forms.ChoiceField(choices=dropdown_choices, widget=forms.Select(attrs={'class': 'form-control'}))
    BINS_CHOICES = [
        (1, '5'),
        (2, '10'),
        (3, '15'),
        (4, '20'),
    ]
    bins_num = forms.ChoiceField(
        label="bins num",
        choices=BINS_CHOICES,
        widget=forms.RadioSelect()
    )
class Form_predict(forms.Form):
    sepal_length = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'sepal length'}), label='', validators=[is_positive_validator])
    sepal_width = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'sepal width'}), label='', validators=[is_positive_validator])
    petal_length = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'petal length'}), label='', validators=[is_positive_validator])
    petal_width = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'petal width'}), label='', validators=[is_positive_validator])