%runs the functions we wrote for homework
function [] = generate_results(filename, reduceAmt, reduceWhat);

%read in image and make a copy
image = imread(filename);
newImage = image;

%set flags
[height width channels] = size(image);
displayFlag = true;

%Loop as many times as required by reduceAmt
i = 1;
while i <= reduceAmt
   if strcmp(reduceWhat, 'WIDTH')
       newImage = reduceWidth(newImage, displayFlag);
       
   else
       newImage = reduceHeight(newImage, displayFlag);
       
   end
    i = i + 1;             %don't forget to increment
    displayFlag = false;    %don't want to make 10000 figures
end

%create title name
titleString = [reduceWhat ' reduced by ' num2str(reduceAmt)];

%display figure and subfigures
figure('Name', titleString);
subplot(1, 3, 1); imshow(image);    %original image
title('Original');
subplot(1, 3, 2); imshow(newImage); %new reduced image
title('Content-Aware');
if strcmp(reduceWhat, 'WIDTH')      %create the naively resized image
   resizedImage = imresize(image,[height width-reduceAmt] );
else
    resizedImage = imresize(image, [height-reduceAmt width] );
end
subplot(1, 3, 3); imshow(resizedImage); %naively resized image
title('Standard');

end