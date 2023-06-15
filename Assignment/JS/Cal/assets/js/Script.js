// var colorQue={
//     colors:[
//         {col:'red'},
//         {col:'blue'},
//         {col:'yellow'},
//         {col:'lightgreen'},
//         {col:'purple'},
//         {col:'black'}
//     ],
//     nextColor:function (){
//         var lastColor=this.colors.pop();
//         this.colors.unshift(lastColor);
//     }
// }
// renderQue();
// function renderQue(){
//     $(`#container`).empty();
//     for (let i = 0; i < colorQue.colors.length; i++) {
//         if (i==0){
//             $(`#up`).css("background-color", colorQue.colors[0].col)
//         }
//         else if (i==(colorQue.colors.length-1)){
//             $(`#down`).css("background-color", colorQue.colors[colorQue.colors.length-1].col)
//         }
//         else {
//             $(`#container`).append(`<div style="background-color: ${colorQue.colors[i].col}"></div>`)
//         }
//     }
//     colorQue.nextColor();
// }
// setInterval(renderQue,200);

// Get the calculator input field and number buttons
const inputField = document.getElementById('inputField');
const numberButtons = document.querySelectorAll('.btn.light.waves-effect');

// Add click event listener to each number button
numberButtons.forEach(button => {
    button.addEventListener('click', () => {
        const clickedNumber = button.value;
        inputField.value += clickedNumber;
    });
});
