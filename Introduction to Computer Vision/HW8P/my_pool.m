%Randyll Bearer:    HW8P:   ConvNet Operations
%This function implements MAX-NON-OVERLAPPING POOLING
function [Output] = my_pool(Input, Pool_Size)

%Parse/Initialize Inputs
[inputX, inputY] = size(Input);
Pool_Size = Pool_Size; %just a scalar

%Compute Output Dimensions
dimension = floor(inputX/Pool_Size);
outputMatrix = zeros(dimension,dimension);
[outputX, outputY] = size(outputMatrix);

%Apply Max-Non-Overlapping Pooling to Input

currentX = 0;
%Loop over the entire Input
for i = 1:Pool_Size:inputX
    currentX = currentX + 1; %Where we are writing to the outputMatrix
    currentY = 0;
    for j = 1:Pool_Size:inputY
       currentY = currentY + 1; %Where we are writing to the outputMatrix
        
       %Loop over Pooling Window
       currentMax = 0;
       for x = i:i+Pool_Size-1
          for y = j:j+Pool_Size-1
              newMax = Input(x,y);
              currentMax = max(currentMax, newMax);
          end
       end
       
       outputMatrix(currentX, currentY) = currentMax; %Update our resultant matrix
       
    end
end


%Compute Output
Output = outputMatrix;
%Debug code to display the resultant matrix
%display(Output);

end
%End of File