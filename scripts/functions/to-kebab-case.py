kebab_case_separator = "-"

def to_kebab_case(normal_string: str):
    return normal_string.lower().replace(" ", kebab_case_separator)
