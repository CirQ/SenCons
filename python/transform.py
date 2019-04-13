import os
from itertools import product
from PIL import Image, ImageEnhance

for d in ['light', 'dark']:
    if not os.path.isdir(d):
        os.mkdir(d)

for imgpath in os.listdir('imgs'):
    dark = Image.open(f'imgs/{imgpath}')
    light = dark.copy()

    if dark.mode == 'P':
        for xy in product(range(512), range(512)):
            p = dark.getpixel(xy) // 4
            light.putpixel(xy, p)
    if dark.mode == 'RGBA':
        phi = lambda p: min(p+192, 255)
        for xy in product(range(512), range(512)):
            px = dark.getpixel(xy)
            p = (phi(px[0]), phi(px[1]), phi(px[2]), px[3])
            light.putpixel(xy, p)

    dark.save(f'dark/dark_{imgpath}')
    light.save(f'light/light_{imgpath}')
