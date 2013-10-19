knit_pat_gen
============
___________
pattern_gen
___________

environment is processing 2.0.3

this is a pattern generator for hacked knitting machine.
you can make a two colors pattern displayed in black and white, 100 x 100px.
(width and height of image is able to change in the code.)

by slider, you can control length of black and white.

by using arrow key or left, right, up and down button in GUI, you can contol the length of repetition.

by clicking mouse, you can change the color for each cells.


______________
pattern_gen_CA
______________

almost same as pattern_gen, but there's a "life game" button.
life game will start from the pattern, one click is one generation.
'L' key is also available for game of life function.

"range" in the GUI controls threshold of survive or death due to depopulation　or congestion.
default is 2 and 3.

"birth" is the number of neighbours when the cell become alive.
but in the code, when neighbours's number is 3, always become alive.
so this parameter is additional.

so default is 23/3

if you move "range" 1 to 3, set 6 to "birth", it will be 13/36.

recommended numbers are 47/36, 47/37 and 12/34. 

Reference
http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
http://ja.wikipedia.org/wiki/%E3%83%A9%E3%82%A4%E3%83%95%E3%82%B2%E3%83%BC%E3%83%A0