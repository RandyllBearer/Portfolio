%Randyll Bearer HW6P

%A. Read in the two images and display them in their own figures.
img1 = imread('keble1.png');
figure;
imshow(img1);
impixelinfo;
title('keble1');

img2 = imread('keble2.png');
figure;
imshow(img2);
impixelinfo;
title('keble2');

%IMPORTANT: impixelinfo gives proper (x,y), may need to convert to (y,x)

%B. Determine four pairs of distinctive points in both images
PA = [280, 122; 163, 77; 194, 155; 267, 144];    %keble1.png
%1. (280,122) %Bottom right Shingle
%2. (163,77)  %Tip of spire on shingled roof
%3. (194,155) %Bottom right of the window to the bottom right of the spire
%4. (267,144) %Bottom right of the window nearest the central tower

PB = [182, 137; 67, 88; 97, 157; 170, 158];    %keble2.png
%1. (182,137)  %Corresponds to point 1 above
%2. (67,88)    %^^^
%3. (97, 157)  %^^^
%4. (170,158)  %^^^

%C. Use estimate_homography() on PA and PB
estimatedHomography = estimate_homography(PA, PB);

%D. Use apply_homography to compute a new pointPrime from a pointRegular
%(194, 171) %Bottom right of the window below point3 above
point1 = [194 171];
point2 = apply_homography(point1, estimatedHomography);

%I'm displaying a diagonal line crossing through the point because I had a
%very difficult time trying to find a single pixel.
figure;
subplot(1,2,1); imshow(img1);
title('keble1.png');
hold on
plot(point1(1,1)-5:point1(1,1)+5, point1(1,2)-5:point1(1,2)+5, 'color', 'g');

subplot(1,2,2); imshow(img2);
title('keble2.png');
hold on
plot(point2(1,1)-5:point2(1,1)+5, point2(1,2)-5:point2(1,2)+5, 'color', 'y');
impixelinfo;

%E. Stitch a mosaic from the two images
%Create a new "canvas" 3 times larger than img2 and put img2 in the middle
[size2X, size2Y, size2Z] = size(img2);
canvas = zeros(size2X*3, size2Y*3, size2Z, 'uint8');

%Plot img2 in the middle of the canvas
for i=1:size2X
    for j=1:size2Y
        for k=1:3   %We need the values of each RGB component per pixel
            canvas(i+size2X-1,j+size2Y-1,k) = img2(i,j,k);
        end        
    end
end

%For each pixel in p1, apply homography to find p2 and draw it on canvas
[sizeX, sizeY, sizeZ] = size(img1);
possiblePoints = zeros(4, 2);
for i=1:sizeX
   for j=1:sizeY    %Calculate corresponding position
      img1point = [i, j];
      img2point = apply_homography(img1point, estimatedHomography);
      
      %Convert from image2 coordinate system to canvas coordinate system
      img2point(1) = img2point(1)+ size2Y-1;
      img2point(2) = img2point(2)+ size2X-1; %These are swapped x,y
      
      %compute all four rounded possibilites
      temp1X = ceil(img2point(1)); 
      temp1Y = ceil(img2point(2));
      
      temp2X = ceil(img2point(1));
      temp2Y = floor(img2point(2));
      
      temp3X = floor(img2point(1));
      temp3Y = ceil(img2point(2));
      
      temp4X = floor(img2point(1));
      temp4Y = floor(img2point(2));
      
      for k=1:3     %Draw points onto canvas
         canvas(temp1X,temp1Y,k) = img1(i,j,k);
         canvas(temp2X,temp2Y,k) = img1(i,j,k);
         canvas(temp3X,temp3Y,k) = img1(i,j,k);
         canvas(temp4X,temp4Y,k) = img1(i,j,k);
         
      end
   end
end

%Display the stitched result
figure;
imshow(canvas);
impixelinfo;

%End of File