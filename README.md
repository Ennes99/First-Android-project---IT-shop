# First-Android-project---IT-shop
An online store where you can log in, buy and sell computers or components. The user also can give feedback about the app.

Antes que nada, mi proyecto está hecho sobre un Nexus 6. Entiendo que no habría problemas con otros móviles, pero sí que puede haber algún
desajuste en la pantalla.

1.- En la activity de Encuesta, quise hacer un AlertDialog. No me funcionó y no tuve más tiempo, pero dejé el código del método (que tendría que
incorporar a otro listener o algo luego) comentado.

3.- El Navigation View lo intenté primero con Fragments. Como no salía y a falta de tiempo, lo hice con más activities, pero igual hay código
referente a los fragments que me haya dejado.

4.- El view del Recycler que selecciono se destruye cuando scrolleo (por la propia naturaleza del recycler). Se guardan sus datos pero el view
que aparece en otro color, al final, puede cambiar. Esto se puede arreglar con un setRecyclable en la definición del Holder, pero no lo vi
pertinente, ya que así el recycler perdería su esencia.
