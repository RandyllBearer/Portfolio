%Randyll Bearer:    HW8P:   ConvNet Operations
function [Output] = my_conv(Image, Filter, Padding, Stride)

%Parse/Initialize Inputs
[imageX, imageY] = size(Image); %Can be treated as 2d
[filterX, filterY] = size(Filter); %Can be treated as 2d
Padding = Padding; %Just a scalar
Stride = Stride; %Just a scalar
%Filter = Filter'; %To make correlation == convolution

%Compute output dimensions
dimension = ((imageX+(2*Padding)-filterX)/Stride)+1; %Formula on slides
outputMatrix = zeros(dimension, dimension);  
[outputX, outputY] = size(outputMatrix);

%Zero-Pad the Image
paddedImage = zeros(imageX+Padding+Padding, imageY+Padding+Padding);
paddedImage(Padding+1:Padding+imageX, Padding+1:Padding+imageY) = Image;
[paddedX, paddedY] = size(paddedImage);
%Debug code to test padding
%display(paddedImage);

%Apply Filter onto Image
currentX = 0;   %Where we are writing into the output
%Loop over the whole paddedImage
for i = ceil(filterX/2):Stride:paddedX-floor(filterX/2)
   currentX = currentX + 1; %Increment where we are writing output
   currentY = 0;
   for j=ceil(filterY/2):Stride:paddedY-floor(filterY/2)
       currentY = currentY + 1; %Increment where we are writing output
       
       %Loop over the window having the filter applied to it
       cumulative = 0;
       currentFilterX = 0;
       for x = i - floor(filterX/2):i+floor(filterX/2)
           currentFilterX = currentFilterX+1;
           currentFilterY = 0;
           for y = j - floor(filterY/2):j+floor(filterY/2)
               currentFilterY = currentFilterY+1;
               %Code to debug loop bounds
               %display("i = " + i); 
               %display("floor(paddedX/2) = " + floor(filterX/2));
               %display("X = " + x);
               %display("Y = " + y);
               %display("currentFilterX = " + currentFilterX);
               %display("currentFilterY = " + currentFilterY);
               cumulative = cumulative + (paddedImage(x,y)*Filter(currentFilterX,currentFilterY));
                
           end
       end
       
       outputMatrix(currentX,currentY) = cumulative; %Update our resultant matrix
 
   end
end

%Compute Output
Output = outputMatrix;
%Debug code to display the resultant matrix
%display(Output);

end
%End of File