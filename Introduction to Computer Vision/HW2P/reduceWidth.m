%adapted from the provided reduceHeight() function.
function [reducedColorImage] = reduceWidth(im, display_flag)

    energyImage = energy_image(im);
    M = cumulative_minimum_energy_map(energyImage, 'VERTICAL');
    vs = find_optimal_vertical_seam(M);
    
    num_rows = size(im, 1);
    num_cols = size(im, 2);
    num_channels = size(im, 3);
    
    assert(length(vs) == num_rows);
        
    reducedColorImage = zeros([num_rows num_cols-1 num_channels]);

    for i = 1:num_rows
        for j = 1:num_channels
            this_row = im(i, :, j);
            this_row(vs(i)) = [];
            reducedColorImage(i, :, j) = this_row;
        end      
    end
    
    reducedColorImage = uint8(reducedColorImage);
    
    if(display_flag)
        figure;
        subplot(1, 3, 1); imagesc(energyImage);
        subplot(1, 3, 2); imagesc(M);
        subplot(1, 3, 3); imshow(im); displaySeam(im, vs, 'VERTICAL')
    end