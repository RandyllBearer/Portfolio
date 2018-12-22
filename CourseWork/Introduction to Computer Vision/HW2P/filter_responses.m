%Randyll Bearer: Programming Homework 2: Part 2 Filter Banks

%IMPORTANT: When trying to display the filter subplot with imshow(), all
%you can see is a black square. You can verify that the filter values
%actually exist in F, it is just that they are such small decimals that
%they are protrayed effectively as black. I didn't want to mess with it by
%multiplying it by 255 or anything to brighten it as that isn't listed in
%the assignment, but I'm pretty certain that is the issue here.


%get the set of filters
F = makeLMfilters;

%create array of filenames
filenames = {'cardinal1.jpg' 'cardinal2.jpg' 'leopard1.jpg' 'leopard2.jpg' 'panda1.jpg' 'panda2.jpg'};
[X Y Z] = size(filenames);

for j = 1:48        %loop through all filters
   
    figure;   %create a new figure for this filter
    filterImage = F(:,:,j);
    subplot(2,4,1); imshow(filterImage );   %plot the filter
    subplot(2,4,2); imshow(255);  %plot the empty space
    
    for i = 1:Y     %loop through all images
    
    image = imread(filenames{i});           %read in image
    image = rgb2gray(image);                %convert to grayscale
    image = imresize(image, [100 100]);     %resize to standard 100x100
    
    newImage = imfilter(image, F(:,:,j) );  %apply filter to image
    subplot(2,4,2+i); imshow(newImage);     %plot filtered image
    title(filenames{i});    %correctly label the image
    
    end
    
    if j == 43   %each set of animals look fairly distinct
        saveas(gcf, 'same_animal_similar.png');
    end
    
    if j == 30   %all of the filtered images look fairly similar
       saveas(gcf, 'different_animals_similar.png'); 
    end
    
end

