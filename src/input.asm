  ; Sample program
	
          ldc.i3     -2
          dup
          stv.u3     0
          stv.u3     1
Loop      ldv.u3     3
          ldc.i8     10
          tlt
          brf.i5     Done
          ldv.u3     1
          .cstring   "kevin"
          ldv.u3     0
          add
          stv.u3     1
          incv.u8    0
		      br.i5      Loop
Done
          halt 