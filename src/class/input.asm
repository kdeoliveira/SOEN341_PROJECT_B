  ; Sample program
	
          ldc.i3     0
          dup
          stv.u3     0
          stv.u3     1 
Loop      ldv.u3     Loop
          ldc.i8     10
          tlt
          brf.i5     Done
          ldv.u3     1
          ldv.u3     0
          add
          stv.u3     1
          incv.u8    0
		  br.i5      Loop
Done
          halt 