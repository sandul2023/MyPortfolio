let result = document.getElementById("result");

function appendValue(value) {
    result.value += value;
}

function calculate() {
    try {
        result.value = eval(result.value);
    } catch (error) {
        result.value = "Error";
    }
}

function clearResult() {
    result.value = "";
}
