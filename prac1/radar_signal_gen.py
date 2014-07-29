#!/usr/python
'''
Created on 04 Mar 2014

@author: Benjamin Hugo
'''
import numpy as np
from pylab import *
from scipy import signal
#constants
assert len(sys.argv) == 3
num_samples = 10000
pulse_spacing = 1000
delta = 500
pulse_height = 100
scaling_factor = 0.2
noise_scaling_factor = 0.1
#generate transmitted signal with pulses spaced pulse_spacing elements apart
transmitted_signal = np.zeros(num_samples).astype(np.float32)
transmitted_signal[::pulse_spacing] = pulse_height
received_signal = np.zeros(num_samples)

#shift the signal by the DSP shift theorem (circular shift)
shift_exp = np.exp(-1.0j*2*np.pi*np.arange(0,num_samples)/num_samples*delta)
received_signal = np.real(np.fft.ifft(np.fft.fft(transmitted_signal) * shift_exp))
received_signal[0:delta] = 0 #lets simulate a non-circular shift by setting the first delta samples to 0

#now add some Gaussian noise to a scaled received signal:
received_signal = (received_signal + np.random.randn(num_samples)*pulse_height*noise_scaling_factor)* 0.2
#demo that argmax of cross-correlation actually works:
xcor = np.zeros(len(received_signal))
for i in range(0,len(received_signal)):
    sum = 0
    for j in range(0, len(transmitted_signal)):
        if (i+j < len(received_signal)):
            sum += received_signal[i+j] * transmitted_signal[j]
    xcor[i] = sum

argmax = 0
for i in range(1,len(xcor)):
    if xcor[argmax] < xcor[i]:
        argmax = i

#save to file
f_transmitted_signal = open(sys.argv[1],'w')
f_received_signal = open(sys.argv[2],'w')
f_transmitted_signal.write(str(num_samples)+" ")
f_received_signal.write(str(num_samples)+" ")
for s in transmitted_signal:
    f_transmitted_signal.write(str(s)+" ")
for s in received_signal:
    f_received_signal.write(str(s)+" ")
f_transmitted_signal.close()
f_received_signal.close()

#plot it all up :D
figure(1)
title("Transmitted signal")
xlabel("Time")
ylabel("Amplitude")
plot(transmitted_signal)
figure(2)
title("Received signal")
xlabel("Time")
ylabel("Amplitude")
plot(received_signal)
figure(3)
title("Cross correlation of received and transmitted signals (shift computed to %d samples)" % argmax)
xlabel("Time")
ylabel("Correlation")
plot(xcor)
show()