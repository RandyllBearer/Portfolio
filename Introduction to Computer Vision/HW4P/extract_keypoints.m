%Taken from HW3P
%Randyll Bearer: HW3P: Harris Corner Detector Function

function [x, y, scores, Ih, Iv] = extract_keypoints(image);

%----------------------- Part 1 -------------------
%Initialize Parameters
k = 0.05;       %to multiply the trace of M
windowSize = 5;  %as detailed in the assignment
half_window_size = floor(windowSize/2); %<2.5 is effectively 2 indices away

%convert image to a grayscale double
image = im2double(image);
grayscaleImage = rgb2gray(image);


%define gradient filters [-1 0 1] / [-1; 0; 1]  
filter_horizontal = [-1; 0; 1];
filter_vertical = [-1 0 1];

%apply filters
Ih = imfilter(grayscaleImage, filter_horizontal);
Iv = imfilter(grayscaleImage, filter_vertical);

%create matrix R to store "cornerness" scores
[height width channels] = size(grayscaleImage); %learn dimensions of image
R = zeros(height, width, channels); %create R

%----------------------- Part 2 --------------------
%"double" for loop, have to double loop alone to go through every pixel
%index

for i = half_window_size+1:height-half_window_size    %taking into account borders?
    for j = half_window_size+1:width-half_window_size %taking into account borders?
    
        M = zeros(2,2);
        for x = i-half_window_size:i+half_window_size 
           for y = j-half_window_size:j+half_window_size
                %calculate and increment M for this pixel
                M(1,1) = M(1,1) + Ih(x, y)^2;
                M(1,2) = M(1,2) + ( Ih(x, y) * Iv(x, y) );
                M(2,1) = M(2,1) + ( Ih(x, y) * Iv(x, y) );
                M(2,2) = M(2,2) + Iv(x, y)^2;
                
           end
        end
        
       %calculate R score for this pixel
       R(i,j) = det(M) - k * trace(M);
    
    end
end

%----------------------- Part 3 --------------------------
%Calculate Threshold (5 times the average method)
threshold = 5 * mean2(R);

%Apply threshold to R
indicesLessThanThreshold = find(R<threshold);
R(indicesLessThanThreshold) = 0;

%----------------------- Part 4 ---------------------------
%Get vector of indices of R to apply non-maximum suppression to
indicesGreaterThanThreshold = find(R > 0);

%Get size of R before reduction for index2subscript translation
sizeR = size(R);

%initialize variables to return;
x = [];
y = [];
scores = [];

%Apply non-maximum suppression to the surviving indices of R
for i = 1:length(indicesGreaterThanThreshold)
    %convert index to subscript
    [row column] = ind2sub(sizeR, indicesGreaterThanThreshold(i));
    %check all 8 neigbors
    isMaximum = 1;
    for neighborRow = row-1:row+1
        for neighborColumn = column-1:column+1
            %compare neighbor pixel to target pixel
            if R(neighborRow, neighborColumn) > R(indicesGreaterThanThreshold(i))
               isMaximum = 0; 
               
            end
            
        end
    end
    %If still maximum, add the index to the cumulative lists of keypoints
    if isMaximum == 1
        x = vertcat(x, column); %remember that row = vertical, not horizontal
        y = vertcat(y, row);
        scores = vertcat(scores, R(indicesGreaterThanThreshold(i)));
    end
    
end

%------------------------------ Part 5 --------------------------
%Display input image
figure
imshow(image);

%Plot the keypoints on top the image
for i = 1:length(x)
   hold on
   plot(x(i), y(i), 'ro', 'MarkerSize', 1.5 * scores(i) );
end
%saveas(gcf, 'test_visual.png'); %TEST LINE

end %end of function