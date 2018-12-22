%Randyll Bearer: Programming Assignment 1: 1/16/2018
%---------------------------

%1. Generate a vector filled with random numbers
disp('Question 1.');
stddev = 5;     %multiply by this
mean = 0;       %add this (effectively nothing)
randomVector = stddev.*randn(1000000,1);
%disp(randomVector); %TEST

%2. Add 1 to every value in randomVector using a loop
disp('Question 2.');
startTime = cputime;
randomVector2 = randomVector;
numElements = size(randomVector2);
numElements = numElements(1);
i = 1;
while i <= numElements
        randomVector2(i) = randomVector2(i) + 1;
        i = i + 1;
end
totalTime = cputime - startTime;
disp(totalTime);

%3. Add 1 to every value in randomVector.
disp('Question 3.');
startTime = cputime;
randomVector3 = randomVector + 1;
totalTime = cputime - startTime;
totalTime

%4. Create two matrices A and B which if added together result in 10x10
%matrix C containing all numbers from 1 to 100.
disp('Question 4.');
A = zeros([10,10]);
%disp(A);    %TEST
B = [1:10; 11:20; 21:30; 31:40; 41:50; 51:60; 61:70; 71:80; 81:90; 91:100];
%disp(B);    %TEST
B = B';     %So that the assertion passes
C = A + B; assert(all(C(:) == (1:100)') == 1); %assertion listed in hw1

%5. Plot the eponential function 2.^x for non-negative even values of x<100
disp('Question 5.');
x = [0:2:99];  %positive even numbers < 100
y = 2.^x;   %typo? should it have been x.^2? 2.^x gets VERY large.
plot(x,y)

%6. print 1-10 random order pause of 1 second between prints using loops
disp('Question 6.');
randomOrder = [0 0 0 0 0 0 0 0 0 0];    %when the last is no longer 0 done
position = 1;   %where we will be adding to randomOrder
while randomOrder(end) == 0
    random = randi([1,10]); %generate a random integer between 1-10
    test = 1;
    
    while 1==1
        if test == 11   %if we don't have it already, add it
            randomOrder(position) = random;
            position = position + 1;
            break
        end
        
        if randomOrder(test) == random  %if duplicate, get a new random
            break
        end
        test = test + 1;
        
    end
    
end

i = 1;
while i <= 10 %Print out result with pauses
   disp(randomOrder(i)); 
   i = i + 1;
   pause(1);
end

%7. Generate two random Matrices and using loops calculate their product
disp('Question 7.');
A = randi([0,1], [5,3]);
B = randi([0,1], [3,5]);
C = zeros([5,5]);
for i=1:5   %Traverse each row in A
  for j=1:5 %Traverse each column in B
     for k=1:3  %The 'depth' of rowA & columnB
        C(i,j) = C(i,j)+A(i,k)*B(k,j);
     end 
   end 
end 
disp('My loop result');
disp(C);
disp('Matlab A*B result');
disp(A*B);

%8. Create the function normalize_rows
disp('Question 8.');

%9. Create the function normalize_columns
disp('Question 9.');

%10. Create the function fib
disp('Question 10.');

%11. Create the function my_unique
disp('Question 11.');

%12. Read in an image as a matrix and write down its dimension.
disp('Question 12.');
image = imread('pittsburgh.png'); % Read a PNG image
disp('Height of Image');
y = size(image, 1);  %num rows / Height
disp(y);
disp('Width of Image / numColumns');
x = size(image, 2);  %num columns / Width
disp(x);
disp('Depth of Image / numChannels');
z = size(image, 3);  %num channels / Depth
disp(z);

%13. Convert the image into grayscale
disp('Question 13.');
grayscaleImage = rgb2gray(image);
%imshow(grayscaleImage); %for testing that the conversion worked.

%14. Without loops, count numPixels in the grayscale image == 6 using sum
disp('Question 14.');
%so a grayscale image only has 1 channel, still working in 2D
numPixelsEqualToSix = sum(grayscaleImage(:) == 6);
disp('# of pixels == 6');
disp(numPixelsEqualToSix)

%15. Find the darkest pixel in the image
disp('Question 15.');
grayscaleVector = grayscaleImage(:);    %convert matrix to single vector
darkestPixelValue = min(grayscaleVector(:));    %find darkest pixel value
IND = find(grayscaleVector == darkestPixelValue);       %find first index of darkest pixel
s = [750, 1500];    %dimensions of image
[Y, X] = ind2sub(s, IND);   %find (row,column) of darkest pixel
disp('Darkest Pixel Value = ');
disp(darkestPixelValue);
disp('Y dimension of darkest pixel = ');
disp(Y);
disp('X dimension of darkest pixel = ');
disp(X);

%16. center a white 31x31 square on top of darkest pixel.
disp('Question 16.');
%so Y,X is the center of our 31x31 square
%top left corner = -15,-15          relative to Y,X
%top right corner = -15, 15
%bottom left corner = 15, -15
%bottom right corner = 15, 15
y = Y - 15;
while y <= Y + 15 %traverse each row
    x = X - 15;
    
    while x <= X + 15;  %for each row, traverse each column
        grayscaleImage(y,x) = 255;  %set the coordinate pixel to white
        
         x = x + 1;
    end
    
   y = y + 1; 
end
%imshow(grayscaleImage);    %for testing that the white square was drawn

%17. Place a 121x121 gray square (px 150) at the center of the prev. image
%without using loops
disp('Question 17.');
%same idea as 16, -60,-60 to 60,60 relative to yCenter,xCenter
yCenter = size(grayscaleImage,1)/2; %divide #rows in half
xCenter = size(grayscaleImage,2)/2; %divide %columns in half
grayscaleImage(yCenter-60:yCenter+60, xCenter-60:xCenter+60) = 150;
%imshow(grayscaleImage);     %for testing that the gray square was drawn

%18. Save the previous image and display it
disp('Question 18.');
figure, imshow(grayscaleImage); %display the new image
saveas(gcf, 'new_image.png');

%19. Compute the scalar average pixel value along each channel then
%subtract the average value per channel in pittsburgh.png. Then save image.
disp('Question 19.');
%Red Channel
redChannel = image(:, :, 1);    %isolate red channel
redAverage = mean2(redChannel); %take the average value of the channel
redChannel = redChannel - redAverage;  %subtract by the average
%Green Channel
greenChannel = image(:, :, 2); 
greenAverage = mean2(greenChannel); 
greenChannel = greenChannel - greenAverage;
%Blue Channel
blueChannel = image(:, :, 3);
blueAverage = mean2(blueChannel);
blueChannel = blueChannel - blueAverage;
%recombine & show
rgbImage = cat(3, redChannel, greenChannel, blueChannel);
figure, imshow(rgbImage);
%save
saveas(gcf, 'mean_sub.png');

