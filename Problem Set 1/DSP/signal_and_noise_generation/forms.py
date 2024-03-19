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
        ('option1', 'Szum o rozkładzie jednostajnym'),
        ('option2', 'Szum Gaussowski'),
        ('option3', 'Sygnał sinusoidalny'),
        ('option4', 'Sygnał sinusoidalny wyprostowany jednopołówkowo'),
        ('option5', 'Sygnał sinusoidalny wyprostowany dwupołówkowo'),
        ('option6', 'Sygnał prostokątny'),
        ('option7', 'Sygnał prostokątny symetryczny'),
        ('option8', 'Sygnał trójkątny'),
        ('option9', 'Skok jednostkowy'),
        ('option10', 'Impuls jednostkowy'),
        ('option11', 'Szum impulsowy'),

    ]
    function = forms.ChoiceField(choices=dropdown_choices, widget=forms.Select(attrs={'class': 'form-control'}))
class Form_predict(forms.Form):
    sepal_length = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'sepal length'}), label='', validators=[is_positive_validator])
    sepal_width = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'sepal width'}), label='', validators=[is_positive_validator])
    petal_length = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'petal length'}), label='', validators=[is_positive_validator])
    petal_width = forms.FloatField(widget=forms.TextInput(attrs={'class': 'form-control', 'placeholder': 'petal width'}), label='', validators=[is_positive_validator])