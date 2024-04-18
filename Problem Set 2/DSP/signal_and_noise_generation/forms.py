from django import forms

def is_positive_validator(value):
    if value <= 0:
        raise forms.ValidationError("This field must be greater than 0")

class Form_add(forms.Form):
    amplitude = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Amplituda (A)'}), label='', validators=[is_positive_validator], initial=0)
    start_time = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas początkowy (t1)'}), label='', validators=[is_positive_validator], initial=0)
    duration = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas trwania sygnału(d)'}), label='', validators=[is_positive_validator], initial=0)
    T = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Okres podstawowy (T)'}), label='', validators=[is_positive_validator], initial=0)
    kw = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Współczynnik wypełnienia (kw)'}),
                                label='', validators=[is_positive_validator], initial=0)
    jump_time = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas skoku (ts)'}),
                                label='', validators=[is_positive_validator], initial=0)
    p = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Prawdopodobieństwo (p)'}),
                                label='', validators=[is_positive_validator], initial=0)
    f = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Częstotliwosc próbkowania'}),
                                label='', validators=[is_positive_validator], initial=50)
    fq = forms.FloatField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Częstotliwosc próbkowania kwantyzacji'}),
        label='', validators=[is_positive_validator], initial=20)
    fsinc = forms.FloatField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Częstotliwosc próbkowania sinc'}),
        label='', validators=[is_positive_validator], initial=10)
    dropdown_choices = [
        ('sinusoidal_signal', 'Sygnał sinusoidalny'),
        ('half_wave_rectifier_signal', 'Sygnał sinusoidalny wyprostowany jednopołówkowo'),
        ('full_wave_rectifier_signal', 'Sygnał sinusoidalny wyprostowany dwupołówkowo'),
        ('rectangular_signal', 'Sygnał prostokątny'),
        ('rectangular_symmetrical_signal', 'Sygnał prostokątny symetryczny'),
        ('triangular_signal', 'Sygnał trójkątny'),
    ]
    function = forms.ChoiceField(choices=dropdown_choices, widget=forms.Select(attrs={'class': 'form-control'}))
    BINS_CHOICES = [
        (5, '5'),
        (10, '10'),
        (15, '15'),
        (20, '20'),
    ]
    bins_num = forms.ChoiceField(
        label="bins num",
        choices=BINS_CHOICES,
        widget=forms.RadioSelect()
    )


class Form_operation(forms.Form):
    amplitude1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Amplituda (A)'}), label='', validators=[is_positive_validator])
    start_time1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas początkowy (t1)'}), label='', validators=[is_positive_validator])
    duration1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas trwania sygnału(d)'}), label='', validators=[is_positive_validator])
    T1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Okres podstawowy (T)'}), label='', validators=[is_positive_validator])
    kw1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Współczynnik wypełnienia (kw)'}),
                                label='', validators=[is_positive_validator])
    jump_time1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas skoku (ts)'}),
                                label='', validators=[is_positive_validator])
    p1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Prawdopodobieństwo (p)'}),
                                label='', validators=[is_positive_validator])
    f1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Częstotliwosc próbkowania'}),
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
    function1 = forms.ChoiceField(choices=dropdown_choices, widget=forms.Select(attrs={'class': 'form-control'}))
    amplitude2 = forms.FloatField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Amplituda (A)'}), label='',
        validators=[is_positive_validator])
    start_time2 = forms.FloatField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas początkowy (t1)'}), label='',
        validators=[is_positive_validator])
    duration2 = forms.FloatField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas trwania sygnału(d)'}), label='',
        validators=[is_positive_validator])
    T2 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Okres podstawowy (T)'}),
                         label='', validators=[is_positive_validator])
    kw2 = forms.FloatField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Współczynnik wypełnienia (kw)'}),
        label='', validators=[is_positive_validator])
    jump_time2 = forms.FloatField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas skoku (ts)'}),
        label='', validators=[is_positive_validator])
    p2 = forms.FloatField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Prawdopodobieństwo (p)'}),
        label='', validators=[is_positive_validator])
    f2 = forms.FloatField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Częstotliwosc próbkowania'}),
        label='', validators=[is_positive_validator])

    function2 = forms.ChoiceField(choices=dropdown_choices, widget=forms.Select(attrs={'class': 'form-control'}))
    BINS_CHOICES = [
        (5, '5'),
        (10, '10'),
        (15, '15'),
        (20, '20'),
    ]

    dropdown_choices_operation = [
        ('+', 'Dodawanie'),
        ('-', 'Odejmowane'),
        ('*', 'Mnożenie'),
        ('/', 'Dzielenie'),
    ]

    operation = forms.ChoiceField(choices=dropdown_choices_operation, widget=forms.Select(attrs={'class': 'form-control'}))
    bins_num = forms.ChoiceField(
        label="bins num",
        choices=BINS_CHOICES,
        widget=forms.RadioSelect()
    )

class File_upload_form(forms.Form):
    file = forms.FileField()