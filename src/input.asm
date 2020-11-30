  ; Sample program
	
          ldc.i3     0
          dup
          stv.u3     0
          stv.u3     1 
Loop      ldv.u3     3
          ldc.i8     10
          tlt
          brf.i5     Done
          ldv.u3     1
          .cstring   b
          ldv.u3     0
          add
          stv.u3     1
          incv.u8    0
		      br.i5      -2
Done
          halt 