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


class Form_distance(forms.Form):
    number_of_measures = forms.IntegerField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Liczba pomiarów'}),
        label='', validators=[is_positive_validator], initial=0)
    time_unit = forms.IntegerField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Jednostka czasowa'}),
        label='', validators=[is_positive_validator], initial=0)
    real_speed = forms.IntegerField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Prędkosc rzeczywista'}),
        label='', validators=[is_positive_validator], initial=0)
    speed_inside = forms.IntegerField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Prędkosc w abstrakcyjnym osrodku'}),
        label='', validators=[is_positive_validator], initial=0)
    T = forms.IntegerField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Okres sygnału'}),
        label='', validators=[is_positive_validator], initial=0)
    number_of_signals = forms.IntegerField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Liczba podstawowych sygnałów'}),
        label='', validators=[is_positive_validator], initial=0)
    f = forms.IntegerField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Częstotliwosć próbkowania'}),
        label='', validators=[is_positive_validator], initial=0)
    buffer = forms.IntegerField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Długosć buforów'}),
        label='', validators=[is_positive_validator], initial=0)
    report = forms.IntegerField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Okres raportowania'}),
        label='', validators=[is_positive_validator], initial=0)

class Form_filter(forms.Form):
    amplitude1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Amplituda (A)'}), label='', validators=[is_positive_validator], initial=0)
    start_time1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas początkowy (t1)'}), label='', validators=[is_positive_validator], initial=0)
    duration1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas trwania sygnału(d)'}), label='', validators=[is_positive_validator], initial=0)
    T1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Okres podstawowy (T)'}), label='', validators=[is_positive_validator], initial=0)
    kw1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Współczynnik wypełnienia (kw)'}),
                                label='', validators=[is_positive_validator], initial=0)
    jump_time1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas skoku (ts)'}),
                                label='', validators=[is_positive_validator], initial=0)
    p1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Prawdopodobieństwo (p)'}),
                                label='', validators=[is_positive_validator], initial=0)
    f1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Częstotliwosc próbkowania'}),
                                label='', validators=[is_positive_validator], initial=0)
    filter_level = forms.IntegerField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Rząd filtru'}),
        label='', validators=[is_positive_validator], initial=0)

    cutoff_frequency = forms.IntegerField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Częstotliwosc odcięcia'}),
        label='', validators=[is_positive_validator], initial=0)

    dropdown_choices = [
        ('sinusoidal_signal', 'Sygnał sinusoidalny'),
        ('half_wave_rectifier_signal', 'Sygnał sinusoidalny wyprostowany jednopołówkowo'),
        ('full_wave_rectifier_signal', 'Sygnał sinusoidalny wyprostowany dwupołówkowo'),
        ('rectangular_signal', 'Sygnał prostokątny'),
        ('rectangular_symmetrical_signal', 'Sygnał prostokątny symetryczny'),
        ('triangular_signal', 'Sygnał trójkątny'),

    ]
    function1 = forms.ChoiceField(choices=dropdown_choices, widget=forms.Select(attrs={'class': 'form-control'}))

    window_choices = [
        ('okno prostokątne', 'okno prostokątne'),
        ('okno Hamminga', 'okno Hamminga'),
    ]

    window = forms.ChoiceField(choices=window_choices, widget=forms.Select(attrs={'class': 'form-control'}))

    filter_choices = [
        ('dolnoprzepustowy', 'dolnoprzepustowy'),
        ('górnoprzepustowy', 'górnoprzepustowy'),
    ]

    filter = forms.ChoiceField(choices=filter_choices, widget=forms.Select(attrs={'class': 'form-control'}))



class Form_operation(forms.Form):
    amplitude1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Amplituda (A)'}), label='', validators=[is_positive_validator], initial=0)
    start_time1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas początkowy (t1)'}), label='', validators=[is_positive_validator], initial=0)
    duration1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas trwania sygnału(d)'}), label='', validators=[is_positive_validator], initial=0)
    T1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Okres podstawowy (T)'}), label='', validators=[is_positive_validator], initial=0)
    kw1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Współczynnik wypełnienia (kw)'}),
                                label='', validators=[is_positive_validator], initial=0)
    jump_time1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas skoku (ts)'}),
                                label='', validators=[is_positive_validator], initial=0)
    p1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Prawdopodobieństwo (p)'}),
                                label='', validators=[is_positive_validator], initial=0)
    f1 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Częstotliwosc próbkowania'}),
                                label='', validators=[is_positive_validator], initial=0)

    dropdown_choices = [
        ('sinusoidal_signal', 'Sygnał sinusoidalny'),
        ('half_wave_rectifier_signal', 'Sygnał sinusoidalny wyprostowany jednopołówkowo'),
        ('full_wave_rectifier_signal', 'Sygnał sinusoidalny wyprostowany dwupołówkowo'),
        ('rectangular_signal', 'Sygnał prostokątny'),
        ('rectangular_symmetrical_signal', 'Sygnał prostokątny symetryczny'),
        ('triangular_signal', 'Sygnał trójkątny'),

    ]
    function1 = forms.ChoiceField(choices=dropdown_choices, widget=forms.Select(attrs={'class': 'form-control'}))
    amplitude2 = forms.FloatField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Amplituda (A)'}), label='',
        validators=[is_positive_validator], initial=0)
    start_time2 = forms.FloatField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas początkowy (t1)'}), label='',
        validators=[is_positive_validator], initial=0)
    duration2 = forms.FloatField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas trwania sygnału(d)'}), label='',
        validators=[is_positive_validator], initial=0)
    T2 = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Okres podstawowy (T)'}),
                         label='', validators=[is_positive_validator], initial=0)
    kw2 = forms.FloatField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Współczynnik wypełnienia (kw)'}),
        label='', validators=[is_positive_validator], initial=0)
    jump_time2 = forms.FloatField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Czas skoku (ts)'}),
        label='', validators=[is_positive_validator], initial=0)
    p2 = forms.FloatField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Prawdopodobieństwo (p)'}),
        label='', validators=[is_positive_validator], initial=0)
    f2 = forms.FloatField(
        widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'Częstotliwosc próbkowania'}),
        label='', validators=[is_positive_validator], initial=0)

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