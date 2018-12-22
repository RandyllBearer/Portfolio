%display the selected type of seam on top of the input image
function [] = displaySeam(im, seam, seamDirection);

%get dimensions of the image so we know how long to plot seams
[height, width, channels] = size(im);


if strcmp(seamDirection, 'VERTICAL')
    %so the vertical seam contains the index for each row, so go down each
    %row < height and select that index pixel.
    hold on
    plot(seam, 1:height, 'color', 'r');
    
else
    %so the horizontal seam contains the index for each picture column, so
    %go across each column < width and select that index pixel.
    hold on
    plot(1:width, seam, 'color', 'r');
    
end


end