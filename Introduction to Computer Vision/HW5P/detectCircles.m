%Randyll Bearer: HW5P Part 2
%"Finds and visualizes circles given an edge map"

function [centers] = detectCircles(im, edges, radius, top_k);
%Initialize Outputs
centers = zeros(top_k, 2);

%Get Number of Detected Edges
[numEdges, thisWillBeFour] = size(edges);

%Declare quantization_value
quantization_value = 5;     %As described in assignment

%Get Dimensions of the Image
[imX, imY, imZ] = size(im);

%Hough Transform
h = zeros(imX, imY);

for i = 1:numEdges  %Loop through every detected edge
    %Compute rough (a,b)
    a = edges(i, 1) - radius * cosd( edges(i,4) );  % Column
    b = edges(i, 2) - radius * sind( edges(i,4) );  % Row
    
    %Polish (a,b)
    %Add 1 because matlab's origin is (1,1), round to an int for index,
    %absolute value, and divide by quantization_value to separate into bins
    a = abs(ceil(a/quantization_value)) + 1;
    b = abs(ceil(b/quantization_value)) + 1;
    
    %Increment the occurences of this bin
    h(a,b) = h(a,b) + 1;  
    
end

%Sort the accumulator array 'h' starting with highest numOccurrences
[unimportant, sortedH] = sort(h(:), 'descend');

%Get the (x,y)'s of top_k, values
[x,y] = ind2sub(size(h), sortedH(1:top_k));

%Fill in Centers[] for output & visualization
for i = (1:top_k)
    centers(i,1) = x(i) * quantization_value;
    centers(i,2) = y(i) * quantization_value;
end

%Visualize Circles
figure;
imshow(im);
viscircles(centers, radius * ones(size(centers, 1), 1));
impixelinfo;
title(['Detected Circle(s) with radius = ' num2str(radius)] );

end
%End of File