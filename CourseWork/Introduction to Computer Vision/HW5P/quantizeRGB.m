%Randyll Bearer: HW5P Part 1
%"performs clustering in the 3-dimensional RGB space, and "quantizes" the image."

%origImg = RGB uint8 image
%k = number of colors to quantize
%outputImg = RGB uint8 image same size as origImg
%meanColors = kx3 array of k centers
%clusterIds = numpixelsx1 (numpixels=numrows*numcolumns) membership vector

function [outputImg, meanColors, clusterIds] = quantizeRGB(origImg, k);
%Initialize Outputs
outputImg = 0;
meanColors = 0;
clusterIds = 0;

%Get dimensions of Original Image
[origImgX, origImgY, origImgZ] = size(origImg);
numPixels = origImgX * origImgY;

%Display Original Image
figure
imshow(origImg);
title('Original Image');

%Resize origImg so that we can apply k-means to it
resizedImg = reshape(origImg, numPixels, 3);
resizedImg = double(resizedImg); %must be of type double for kmeans() to work

%Apply k-means to the resized image
clusterIds = kmeans(resizedImg, k);

%Calculate the average RGB value per cluster and set pixels = to it
newImg = origImg;

i = 1;
while i <= k %Need to compute the average RGB for all pixels in a cluster
    numMembersInCluster = 0;
    sumMembersR = 0;
    sumMembersG = 0;
    sumMembersB = 0;
    
    %iterate over every pixel, summing its RGB for each cluster
    j = 1;
    while j <= numPixels
        if clusterIds(j) == i
            numMembersInCluster = numMembersInCluster + 1;
            
            %We need subscript in order to get individual RGB's
            [x,y] = ind2sub([origImgX, origImgY, origImgZ], j);
            
            %Cast as double so that we can sum above 255
            sumMembersR = sumMembersR + double(newImg(x,y,1));
            sumMembersG = sumMembersG + double(newImg(x,y,2));
            sumMembersB = sumMembersB + double(newImg(x,y,3));
            
        end
        j = j + 1;
    end
    
    %compute the cluster's average RGB (Floor because we need pixel ints)
    clusterAverageR = floor(sumMembersR / numMembersInCluster); 
    clusterAverageG = floor(sumMembersG / numMembersInCluster);
    clusterAverageB = floor(sumMembersB / numMembersInCluster);

    %iterate over every pixel and set its value to its cluster's mean
    j = 1;
    while j <= numPixels
        if clusterIds(j) == i
            [x,y] = ind2sub([origImgX, origImgY, origImgZ], j);
            
            newImg(x,y,1) = clusterAverageR;
            newImg(x,y,2) = clusterAverageG;
            newImg(x,y,3) = clusterAverageB;
            
        end
        j = j + 1;
    end
    
    i = i + 1; 
end

%Display 'quantized' image
figure
imshow(newImg);
title(['Quantized Image with k = ' num2str(k)] );

end
%End of File