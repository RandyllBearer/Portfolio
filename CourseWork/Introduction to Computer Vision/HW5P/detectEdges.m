function [edges] = detectEdges(im, threshold)
      
    im = rgb2gray(im);

    % compute gradients
    fil_x = [1 0 -1];
    Gx = imfilter(double(im), fil_x, 'same');    
    fil_y = fil_x';
    Gy = imfilter(double(im), fil_y, 'same');
              
    % compute gradient magnitude
    grad_mag = sqrt(Gx.^2 + Gy.^2);

    % compute gradient orientation
    grad_orient = atand(Gy ./ Gx);
                
    % set default threshold (optional)
    if(nargin == 1)
        threshold = 2 * mean(grad_mag(:));
    end
    
    % find edge points
    edge_inds = find(grad_mag(:) >= threshold);
    [y_inds, x_inds] = ind2sub(size(im), edge_inds);
    edge_mag = grad_mag(edge_inds);
    edge_orient = grad_orient(edge_inds);
    edges = [x_inds y_inds edge_mag edge_orient];
    
    % display edges
    not_edge_inds = find(grad_mag(:) < threshold);
    im(not_edge_inds) = 0;
    im(edge_inds) = edge_mag;
    figure; imshow(im)
    