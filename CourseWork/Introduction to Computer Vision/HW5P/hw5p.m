%Randyll Bearer: HW5P
%Implemented functions quantizeRGB() and detectCircles()
%detectEdges() Provided

%README: This is just a sample driver program I used when testing my
%functions, feel free to disregard this and go straight to quantizeRGB()
%and detectCircles().

%Sample image quantizing
testImage = imread('fish.jpg');
quantizeRGB(testImage, 24);

%Sampe circle detection
testImage1 = imread('jupiter.jpg');
testImage2 = imread('egg.jpg');
[edges] = detectEdges(testImage1, 5);
[centers] = detectCircles(testImage1, edges, 10, 20);

%End of File