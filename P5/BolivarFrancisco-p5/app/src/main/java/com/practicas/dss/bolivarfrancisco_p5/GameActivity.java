package com.practicas.dss.bolivarfrancisco_p5;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Date;

public class GameActivity extends Activity {
    private static final long INTERVALO_CLICK = 1000;

    private DBHelperResults db = DBHelperResults.getInstance(this); // Base de datos

    private int aciertos; // número de aciertos
    private int fallos; // número de fallos
    private int id; // id de la pregunta actual
    private int limite; // número de preguntas que se responderán
    private int vidas; // número de vidas
    private int maxVidas; //Las vidas con las que se empieza
    private int modo; // modo de juego (0 normal, 1 con vidas)
    private Pregunta pregunta; // pregunta actual
    private TextView nTiempo; // TextView con el tiempo restante
    private Button option0; // Botón primera opción
    private Button option1; // Botón segunda opción
    private Button option2; // Botón tercera opción
    private Button option3; // Botón cuarta opción
    private ImageButton play; // Botón play
    private ImageButton pause; // Botón pause
    private ImageButton stop; // Botón stop
    private CountDownTimer cronometro; // Cronómetro con la cuenta atrás
    private SoundPool soundPool; // Sonido de acierto o fallo
    private int spAciertoId; // Identificador de sonido de acierto
    private int spFalloId; // Identificador de sonido de fallo
    private MediaPlayer mediaPlayer;
    private long mLastClickTime = 0; // Variable para controlar el tiempo entre pulsaciones

    // Método llamado al crear la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        Bundle extras = getIntent().getExtras(); // Se obtienen los parámetros recibidos

        // Se busca el texto de tiempo
        nTiempo = (TextView) findViewById(R.id.nTime);

        maxVidas=5;
        vidas = 5;
        modo = extras.getInt("mod");

        if (modo == 0) {
            limite = extras.getInt("num"); // Se recoge el parámetro con el límite de preguntas
            LinearLayout lHearts = (LinearLayout) findViewById(R.id.heartsLayout);
            lHearts.setVisibility(View.GONE); //Se ocultan los corazones
            if (limite > Preguntas.size() || limite < 1) { // Número incorrecto de preguntas
                limite = Preguntas.size(); // Se juegan todas las preguntas
                Toast.makeText(this, R.string.question_readjustment, Toast.LENGTH_SHORT).show();
            }
        } else {
            limite = Preguntas.size();
        }

        Preguntas.shuffle(); // Se barajan las preguntas

        aciertos = fallos = id = 0; // se inicializan las variables a 0
        pregunta = Preguntas.getPregunta(id); // se escoge la pregunta número id
        pregunta.shuffle();

        // Se buscan los botones
        option0 = (Button) findViewById(R.id.option0);
        option1 = (Button) findViewById(R.id.option1);
        option2 = (Button) findViewById(R.id.option2);
        option3 = (Button) findViewById(R.id.option3);
        play = (ImageButton) findViewById(R.id.play);
        pause = (ImageButton) findViewById(R.id.pause);
        stop = (ImageButton) findViewById(R.id.stop);

        // Se crean los sonidos de acierto y fallo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // Usa el nuevo constructor
            AudioAttributes aa = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(2)
                    .setAudioAttributes(aa)
                    .build();
            spAciertoId = soundPool.load(this, R.raw.correct, 1);
            spFalloId = soundPool.load(this, R.raw.wrong, 1);
        } else { // Usa un constructor deprecated a partir de Lollipop pero válido en versiones anteriores
            soundPool = new SoundPool(2, AudioManager.STREAM_NOTIFICATION, 1);
            spAciertoId = soundPool.load(this, R.raw.correct, 1);
            spFalloId = soundPool.load(this, R.raw.wrong, 1);
        }

        actualizarVistas(); // Se actualizan las vistas con la pregunta correspondiente

        // Listener de los botones
        option0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < INTERVALO_CLICK) { // Se ha pulsado un botón hace menos de INTERVALO_CLICK milisegundos
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime(); // Se actualiza la última pulsación
                cronometro.cancel();
                elegirRespuesta(0);
            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < INTERVALO_CLICK) { // Se ha pulsado un botón hace menos de INTERVALO_CLICK milisegundos
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime(); // Se actualiza la última pulsación
                cronometro.cancel();
                elegirRespuesta(1);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < INTERVALO_CLICK) { // Se ha pulsado un botón hace menos de INTERVALO_CLICK milisegundos
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime(); // Se actualiza la última pulsación
                cronometro.cancel();
                elegirRespuesta(2);
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < INTERVALO_CLICK) { // Se ha pulsado un botón hace menos de INTERVALO_CLICK milisegundos
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime(); // Se actualiza la última pulsación
                cronometro.cancel();
                elegirRespuesta(3);
            }
        });

        // Listener de los botones del reproductor
        play.setOnClickListener(new View.OnClickListener() { // Play
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < INTERVALO_CLICK) { // Se ha pulsado un botón hace menos de INTERVALO_CLICK milisegundos
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime(); // Se actualiza la última pulsación
                playMusic();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() { // Pause
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < INTERVALO_CLICK) { // Se ha pulsado un botón hace menos de INTERVALO_CLICK milisegundos
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime(); // Se actualiza la última pulsación
                pauseMusic();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() { // Stop
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < INTERVALO_CLICK) { // Se ha pulsado un botón hace menos de INTERVALO_CLICK milisegundos
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime(); // Se actualiza la última pulsación
                stopMusic();
            }
        });

    }

    @Override
    // Método cuando se pulsa el botón atrás
    public void onBackPressed() {
        // String auxiliares que se mostrarán en el mensaje
        String areYouSureMessage = getString(R.string.exit_game_confirmation);
        String successMessage = getString(R.string.num_success) + aciertos;
        String errorsMessage = getString(R.string.num_errors) + fallos;

        // Se muestra un AlertDialog con los resultados finales y un botón para volver al menu principal
        new MaterialDialog.Builder(this)
                .title(R.string.exit_game)
                .content(areYouSureMessage + "\n\n" + successMessage + "\n" + errorsMessage) // Mensaje de despedida con los aciertos y fallos que se llevan
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        cronometro.cancel();
                        if (pregunta.getTipo() == 2) { // Hay música
                            destruirMediaPlayer(); // Finalizar y liberar música
                        }
                        finalizarPartida(); // Se finaliza la partida
                    }
                })
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .cancelable(false) // Pulsar fuera del AlertDialog no lo desactiva
                .show();
    }

    // Método llamado al pulsar un botón de respuesta
    private void elegirRespuesta(int id) {
        if (pregunta.getTipo() == 2) { // Hay música
            destruirMediaPlayer(); // Finalizar y liberar música
        }
        if (pregunta.getRespuestaCorrecta() == id) { // Acierto
            // Se colorea el botón de verde
            switch(id) {
                case 0:
                    option0.setBackgroundResource(R.drawable.btn_success);
                    break;
                case 1:
                    option1.setBackgroundResource(R.drawable.btn_success);
                    break;
                case 2:
                    option2.setBackgroundResource(R.drawable.btn_success);
                    break;
                case 3:
                    option3.setBackgroundResource(R.drawable.btn_success);
                    break;
                default:
                    break;
            }
            respuestaCorrecta();
        } else { // Fallo
            // Se colorea el botón de rojo
            switch(id) {
                case 0:
                    option0.setBackgroundResource(R.drawable.btn_error);
                    break;
                case 1:
                    option1.setBackgroundResource(R.drawable.btn_error);
                    break;
                case 2:
                    option2.setBackgroundResource(R.drawable.btn_error);
                    break;
                case 3:
                    option3.setBackgroundResource(R.drawable.btn_error);
                    break;
                default:
                    break;
            }
            respuestaIncorrecta();
        }
    }

    // Método llamado al pulsar en play
    private void playMusic() {
        mediaPlayer.start(); // Se vuelve a reproducir
        pause.setEnabled(true); // Se habilita el botón de pause
        play.setEnabled(false); // Se deshabilita el botón de play
    }

    // Método llamado al pulsar en pause
    private void pauseMusic() {
        mediaPlayer.pause(); // Se para
        play.setEnabled(true); // Se habilita el botón de play
        pause.setEnabled(false); // Se deshabilita el botón de pause
    }

    // Método llamado al pulsar en stop
    private void stopMusic() {
        mediaPlayer.seekTo(0); // Se vuelve al principio
        mediaPlayer.start(); // Se inicia
        play.setEnabled(true); // Se habilita el botón de play
        pause.setEnabled(false); // Se deshabilita el botón de pause
    }

    private void actualizaCorazones(){
        LinearLayout lHearts = (LinearLayout) findViewById(R.id.heartsLayout);

        // Se ponen invisibles los corazones perdidos
        for(int i=vidas; i<maxVidas;i++){
            lHearts.getChildAt(i).setVisibility(View.INVISIBLE);
        }

    }

    // Método para actualizar la actividad cada vez que se cambia de pregunta
    private void actualizarVistas() {
        // Vidas
        if (modo == 1) {
            actualizaCorazones();
        }

        // Se da el estilo de botón predeterminado a todos los botones
        option0.setBackgroundResource(R.drawable.btn_default);
        option1.setBackgroundResource(R.drawable.btn_default);
        option2.setBackgroundResource(R.drawable.btn_default);
        option3.setBackgroundResource(R.drawable.btn_default);

        // Se cargan la imagen y el RelativeLayout donde se encuentran los controles de la música
        ImageView image = (ImageView) findViewById(R.id.image);
        RelativeLayout music = (RelativeLayout) findViewById(R.id.music);

        if (this.pregunta.getTipo() == 0 || this.pregunta.getTipo() == 1) {
            music.setVisibility(View.GONE); // Los controles de la música desaparecen
            image.setVisibility(View.VISIBLE); // La imagen se hace visible
            if (this.pregunta.getTipo() == 1) { // Si es tipo pregunta con imagen se carga el recurso
                image.setImageResource(getImageId(this, this.pregunta.getRecurso()));
            } else { // en caso contrario se carga la imagen genérica
                image.setImageResource(R.drawable.generic_question);
            }
        } else { // Si la pregunta es de música
            image.setVisibility(View.GONE); // La imagen desaparece
            music.setVisibility(View.VISIBLE); // Los controles de música se hacen visibles
            mediaPlayer = MediaPlayer.create(this, getSongId(this, this.pregunta.getRecurso())); // Se crea el media player
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(true); // Se habilita el Looping para que se repita
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) { // Cuando esté preparado se inicia
                    mp.start();
                }
            });
            play.setEnabled(false); // Se deshabilita el botón play, pues actualmente está sonando
        }

        // Se carga la pregunta y se le da el texto correspondiente
        TextView pregunta = (TextView) findViewById(R.id.question);
        pregunta.setText(this.pregunta.getPregunta());

        // Se carga el texto en todos los botones
        option0.setText(this.pregunta.getRespuesta(0));
        option1.setText(this.pregunta.getRespuesta(1));
        option2.setText(this.pregunta.getRespuesta(2));
        option3.setText(this.pregunta.getRespuesta(3));

        nTiempo.setTextColor(getResources().getColor(R.color.labelTextColor));
        // Se genera la cuenta atrás
        cuentaAtras();
    }

    // Método que se activa cuando la respuesta pulsada es correcta
    private void respuestaCorrecta() {
        // Se actualizan las variables correspondientes
        aciertos++;
        id++;

        // String auxiliares que se mostrarán en el mensaje
        String successMessage = getString(R.string.num_success) + aciertos;
        String errorsMessage = getString(R.string.num_errors) + fallos;
        String message = successMessage + "\n" + errorsMessage;

        // Sonido de acierto
        soundPool.play(spAciertoId, 0.25f, 0.25f, 1, 0, 1);

        // Se muestra un AlertDialog con los resultados parciales y un botón para continuar
        new MaterialDialog.Builder(this)
                .title(R.string.success_message)
                .content(message) // Mensaje con los aciertos y fallos que se llevan
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        if (id == limite) { // Se ha respondido todas las preguntas
                            juegoCompletado();
                        } else { // Se pasa a la siguiente pregunta
                            siguientePregunta();
                        }
                    }
                })
                .positiveText(R.string.continue_game)
                .cancelable(false) // Pulsar fuera del AlertDialog no lo desactiva
                .show();
    }


    // Método que se activa cuando la respuesta pulsada es correcta
    private void respuestaIncorrecta() {
        // Se actualizan las variables correspondientes
        fallos++;
        id++;
        if (modo == 1) {
            vidas--;
        }

        // String auxiliares que se mostrarán en el mensaje
        String successMessage = getString(R.string.num_success) + aciertos;
        String errorsMessage = getString(R.string.num_errors) + fallos;
        String message = successMessage + "\n" + errorsMessage;

        // Sonido de fallo
        soundPool.play(spFalloId, 0.25f, 0.25f, 1, 0, 1);

        // Se muestra un AlertDialog con los resultados parciales y un botón para continuar
        new MaterialDialog.Builder(this)
                .title(R.string.error_message)
                .content(message) // Mensaje con los aciertos y fallos que se llevan
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        if (id == limite || (modo == 1 && vidas < 1)) { // Se ha respondido todas las preguntas
                            juegoCompletado();
                        } else { // Se pasa a la siguiente pregunta
                            siguientePregunta();
                        }
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        finalizarPartida();
                    }
                })
                .positiveText(R.string.continue_game)
                .negativeText(R.string.exit_game)
                .cancelable(false) // Pulsar fuera del AlertDialog no lo desactiva
                .show();
    }

    // Método que se activa cuando se responden todas las preguntas
    private void juegoCompletado() {
        // String auxiliares que se mostrarán en el mensaje
        String finishMessage = getString(R.string.finish_message);
        if (modo == 1 && vidas < 1) {
            finishMessage = getString(R.string.finish_message_no_lifes);
        }
        String successMessage = getString(R.string.num_success) + aciertos;
        String errorsMessage = getString(R.string.num_errors) + fallos;
        String message = finishMessage + "\n\n" + successMessage + "\n" + errorsMessage;

        new MaterialDialog.Builder(this)
                .title(R.string.finish_title)
                .content(message) // Mensaje de despedida con los aciertos y fallos que se llevan
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        finalizarPartida(); // Se finaliza la partida
                    }
                })
                .positiveText(R.string.exit_game)
                .cancelable(false) // Pulsar fuera del AlertDialog no lo desactiva
                .show();
    }

    // Método para cambiar de pregunta y actualizar los campos de la actividad
    private void siguientePregunta() {
        pregunta = Preguntas.getPregunta(id);
        pregunta.shuffle();
        actualizarVistas();
    }

    // Método que se activa cuando se finaliza la partida
    private void finalizarPartida() {
        db.add(new Date(), aciertos, fallos);
        finish();
    }

    // Método que destruye el mediaPlayer
    private void destruirMediaPlayer() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
    }

    // Método para obtener el id de un recurso en forma de imagen a partir de un String
    private int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    // Método para obtener el id de un recurso en forma de sonido a partir de un String
    private int getSongId(Context context, String songName) {
        return context.getResources().getIdentifier("raw/" + songName, null, context.getPackageName());
    }

    // Método para realizar la cuenta atrás
    private void cuentaAtras() {
        cronometro = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                nTiempo.setText(Long.toString(millisUntilFinished/1000));
                if(millisUntilFinished < 6000) {
                    nTiempo.setTextColor(getResources().getColor(R.color.buttonErrorColor));
                }
            }
            @Override
            public void onFinish() {
                respuestaIncorrecta();
            }
        }.start();
    }
}
