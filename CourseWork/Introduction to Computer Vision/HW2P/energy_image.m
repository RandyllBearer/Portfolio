%computes the energy at each pixel using the magnitude of the x and y
%gradients
function [energyImage, Ix, Iy] = energy_image(im);

%convert image to grayscale and double
newIM = rgb2gray(im);
newIM = im2double(newIM);

%define sobel filters [1 2 1; 0 0 0; -1 -2 -1]
filt_sobel_horizontal = fspecial('sobel');
filt_sobel_vertical = fspecial('sobel')';

%apply filters
Ix = imfilter(newIM, filt_sobel_horizontal);
Iy = imfilter(newIM, filt_sobel_vertical);

%create energyImage using formula from reading with L2 norm
energyImage = sqrt(Ix.^2 + Iy.^2);

end