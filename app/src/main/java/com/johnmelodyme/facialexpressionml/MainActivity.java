package com.johnmelodyme.facialexpressionml;
/*
 *  I Actually hate creating this app but My baby
 *  encourage me and say that I can ,
 *  SO I do So.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.johnmelodyme.facialexpressionml.Helper.GraphicOverlay;
import com.johnmelodyme.facialexpressionml.Helper.RectOverlay;
import com.muddzdev.styleabletoast.StyleableToast;
import com.roger.catloadinglibrary.CatLoadingView;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

/**
 * @Author : John Melody Melissa
 * @Copyright: John Melody Melissa  © Copyright 2020
 * @INPIREDBYGF : Sin Dee <3
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private static final String FILE_NAME = "FacialExpressionDetectionResult.txt";
    private static final int CAMERA_REQUEST_CODE = 0x1;
    private StorageReference CLOUD_STORAGE;
    private TextToSpeech TEXT_TO_SPEECH;
    private MediaPlayer AI_SAY;
    private android.app.AlertDialog ALERT_PROMPT, LOADING, UPLOADING;
    private FirebaseAuth FIREBASEAUTH;
    private CameraView CAMERA_VIEW;
    private GraphicOverlay GRAPHIC_OVERLAY;
    private RadioButton OK, SOSO, Pre_Severe, Severe, RADhappy, RADsad, RADneutral, RADother;
    private RadioGroup RG_Emotion;
    private Button Analyse;
    private TextView Emotion_result, ACCURACY, E_MOTION, EMOJI;
    private EditText COMMENT;
    Thread thread, g;
    private String E;
    private String Data[];
    private int D;
    private Handler handler;
    Random ran = new Random();
    float d_classification;
    int TIME = 0x5;
    private GraphView GRAPH;
    private LineGraphSeries<DataPoint> DATA;
    private CatLoadingView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "FE" + "onCreate: Starting Application.");

        TEXT_TO_SPEECH = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int STATUS) {
                if (TextToSpeech.ERROR != STATUS) {
                    TEXT_TO_SPEECH.setLanguage(Locale.UK);
                }
            }
        });

        CLOUD_STORAGE = FirebaseStorage.getInstance().getReference();
        FIREBASEAUTH = FirebaseAuth.getInstance();
        Emotion_result = findViewById(R.id.emotion);
        CAMERA_VIEW = findViewById(R.id.CAMERA);
        GRAPHIC_OVERLAY = findViewById(R.id.GO);
        ACCURACY = findViewById(R.id.cal);
        RADhappy = findViewById(R.id.Happy);
        RADneutral = findViewById(R.id.Neutral);
        RADsad = findViewById(R.id.Sad);
        RADother = findViewById(R.id.Other);
        RG_Emotion = findViewById(R.id.RADIO_GROUP_EMOTION);
        COMMENT = findViewById(R.id.Comment);
        OK = findViewById(R.id.ok);
        SOSO = findViewById(R.id.soso);
        Pre_Severe = findViewById(R.id.presevere);
        Severe = findViewById(R.id.severe);
        Analyse = findViewById(R.id.analyse);
        E_MOTION = findViewById(R.id.e_motion);
        EMOJI = findViewById(R.id.emoji);
        handler = new Handler();
        GRAPH = findViewById(R.id.graph);
        d_classification = ran.nextFloat();
        DATA = new LineGraphSeries<>();
        GRAPH.addSeries(DATA);
        Viewport viewport = GRAPH.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0x0);
        viewport.setMaxY(0x1);
        viewport.setScrollable(true);
        Data = getResources().getStringArray(R.array.emo);
        D = new Random().nextInt(Data.length);
        E = Data[D];
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(0x3e8);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO CONVERT INT TO STRING
                                Random random = new Random();
                                //int RandomData = R.nextInt(EMOTIONS.length);
                                double classification;
                                String Data[];
                                int D, COUNT;
                                final String E, S;
                                COUNT = 0x0;
                                Data = getResources().getStringArray(R.array.emo);
                                D = new Random().nextInt(Data.length);
                                E = Data[D];
                                classification = random.nextDouble();
                                S = String.valueOf(classification);
                                ACCURACY.setText(S);
                                Emotion_result.setText(E);
                                //Log.d(TAG, "USER EMOTION : " + E);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    Emotion_result.setText("");
                }
            }
        };
        thread.start();
        //thread.stop();

        ALERT_PROMPT = new SpotsDialog
                .Builder()
                .setContext(MainActivity.this)
                .setMessage("Logging Out...")
                .setCancelable(false)
                .build();

        UPLOADING = new SpotsDialog
                .Builder()
                .setContext(MainActivity.this)
                .setMessage("Uploading to the server...")
                .setCancelable(false)
                .build();

        LOADING = new SpotsDialog
                .Builder()
                .setContext(MainActivity.this)
                .setMessage("Loading...")
                .setCancelable(false)
                .build();

        CAMERA_VIEW.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {
            }
            @Override
            public void onError(CameraKitError cameraKitError) {
            }
            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                LOADING.show();
//                KProgressHUD hud = KProgressHUD.create(MainActivity.this)
//                        .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
//                        .setLabel("Please wait")
//                        .setMaxProgress(30)
//                        .show();
//                hud.show();
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, CAMERA_VIEW.getWidth(), CAMERA_VIEW.getHeight(), false);
                CAMERA_VIEW.stop();
                PROCESS_FACE_DETECTION(bitmap);
            }
            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {
            }
        });

        CAMERA_VIEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Emotion_result.setText("");
                CAMERA_VIEW.start();
                CAMERA_VIEW.captureImage();
                GRAPHIC_OVERLAY.clear();
            }
        });

        Analyse.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                AI_SAY = MediaPlayer.create(MainActivity.this, R.raw.analyzing);
                AI_SAY.start();
                AI_SAY.stop();
                final String DONE, load;
                String USER_STRESS_VALUE, USER_EMOTION_PREDICTION;

                USER_STRESS_VALUE = E_MOTION.getText()
                        .toString()
                        .trim();
                USER_EMOTION_PREDICTION =EMOJI.getText()
                        .toString()
                        .trim();

                DONE = v.getResources().getString(R.string.analyse_after_done);
                load = v.getResources().getString(R.string.analyse_after);
                CAMERA_VIEW.start();
                CAMERA_VIEW.captureImage();
                GRAPHIC_OVERLAY.clear();
                Analyse.setText(load);
//              if (!OK.isChecked() || !SOSO.isChecked() || !Pre_Severe.isChecked() || !Severe.isChecked()){
//                    ROTIBAKAR("Please Select Your Emotion");
//              } else if (!RADhappy.isChecked() || !RADsad.isChecked() || !RADneutral.isChecked() || !RADother.isChecked()){
//                    ROTIBAKAR("Please Select Your Stress Level");
//              } else {}
                if (OK.isChecked() || RADhappy.isChecked()) {
                    Analyse.setText(DONE);
                    E_MOTION.setText("Stress-Level: 0%");
                } else if (SOSO.isChecked() || RADhappy.isChecked()) {
                    Analyse.setText(DONE);
                    E_MOTION.setText("Stress-Level: 25%");
                } else if (Pre_Severe.isChecked() || RADhappy.isChecked()) {
                    Analyse.setText(DONE);
                    E_MOTION.setText("Stress-Level: 50%");
                } else if (Severe.isChecked() || RADhappy.isChecked()) {
                    Analyse.setText(DONE);
                    E_MOTION.setText("Stress-Level: 100%");
                } else if (OK.isChecked() || RADsad.isChecked()) {
                    Analyse.setText(DONE);
                    E_MOTION.setText("Stress-Level: 0%");
                } else if (SOSO.isChecked() || RADsad.isChecked()) {
                    Analyse.setText(DONE);
                    E_MOTION.setText("Stress-Level: 25%");
                } else if (Pre_Severe.isChecked() || RADsad.isChecked()) {
                    Analyse.setText(DONE);
                    E_MOTION.setText("Stress-Level: 50%");
                } else if (Severe.isChecked() || RADsad.isChecked()) {
                    Analyse.setText(DONE);
                    E_MOTION.setText("Stress-Level: 100%");
                } else if (OK.isChecked() || RADneutral.isChecked()) {
                    Analyse.setText(DONE);
                    E_MOTION.setText("Stress-Level: 0%");
                } else if (SOSO.isChecked() || RADneutral.isChecked()) {
                    Analyse.setText(DONE);
                    E_MOTION.setText("Stress-Level: 25%");
                } else if (Pre_Severe.isChecked() || RADneutral.isChecked()) {
                    Analyse.setText(DONE);
                    E_MOTION.setText("Stress-Level: 50%");
                } else if (Severe.isChecked() || RADneutral.isChecked()) {
                    Analyse.setText(DONE);
                    E_MOTION.setText("Stress-Level: 100%");
                } else if (OK.isChecked() || RADother.isChecked()) {
                    Analyse.setText(DONE);
                    E_MOTION.setText("Stress-Level: 0%");
                } else if (SOSO.isChecked() || RADother.isChecked()) {
                    Analyse.setText(DONE);
                    E_MOTION.setText("Stress-Level: 25%");
                } else if (Pre_Severe.isChecked() || RADother.isChecked()) {
                    Analyse.setText(DONE);
                    E_MOTION.setText("Stress-Level: 50%");
                } else if (Severe.isChecked() || RADother.isChecked()) {
                    Analyse.setText(DONE);
                    E_MOTION.setText("Stress-Level: 100%");
                } else {
                    Log.d(TAG, "FE" + " classification null");
                }
                Log.d(TAG, "FE" + " User is :  " + E);
                EMOJI.setText("User is :  " + "\"" + E + "\"");
                thread.interrupt();
            }
        });


        // TODO: setOnLongClickListener : ANALYSE:
        Analyse.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String click;
                click = getResources().getString(R.string.analyse);
                E_MOTION.setText(" ");
                Analyse.setText(click);
                thread.start();
                GRAPHIC_OVERLAY.clear();
                CAMERA_VIEW.stop();
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);

        if (requestcode == CAMERA_REQUEST_CODE && resultcode == RESULT_OK){

            UPLOADING.show();
            Uri uri = data.getData();
            StorageReference filepath = CLOUD_STORAGE.child("FACES").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    UPLOADING.dismiss();
                    new StyleableToast
                            .Builder(MainActivity.this)
                            .text("Exported to the database...{ok}")
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.rgb(255,20,147))
                            .show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    UPLOADING.dismiss();
                    new StyleableToast
                            .Builder(MainActivity.this)
                            .text("404: Server Error :( ")
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.rgb(255,20,147))
                            .show();
                }
            });
        }
    }




    private boolean isExternalStorageAvailable(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            Log.d(TAG, "SDCARD WRITABLE======>");
            return true;
        } else {
            return false;
        }
    }

    private void PROCESS_FACE_DETECTION(Bitmap bitmap) {
        FirebaseVisionImage firebaseVisionImage;
        firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionFaceDetectorOptions firebaseVisionFaceDetectorOptions;
        firebaseVisionFaceDetectorOptions = new FirebaseVisionFaceDetectorOptions
                .Builder()
                .build();
        FirebaseVisionFaceDetector firebaseVisionFaceDetector = FirebaseVision.getInstance()
                .getVisionFaceDetector(firebaseVisionFaceDetectorOptions);
        firebaseVisionFaceDetector.detectInImage(firebaseVisionImage)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                        getFaceResult(firebaseVisionFaces);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //ROTIBAKAR("Error: " + exception.getMessage());
                new StyleableToast
                        .Builder(MainActivity.this)
                        .text("Error: " + exception.getMessage())
                        .textColor(Color.WHITE)
                        .backgroundColor(Color.rgb(0xff, 0x14, 0x93))
                        .show();
                Log.d(TAG, "FE" + ":" + " Error: " + exception.getMessage());
            }
        });
    }

    private void getFaceResult(List<FirebaseVisionFace> firebaseVisionFaces) {
        int COUNTER = 0x0;
        for (FirebaseVisionFace visionFace : firebaseVisionFaces) {
            Rect rect = visionFace.getBoundingBox();
            RectOverlay rectOverlay = new RectOverlay(GRAPHIC_OVERLAY, rect);
            GRAPHIC_OVERLAY.add(rectOverlay);
            COUNTER = COUNTER + 0x1;
        }
        LOADING.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.signout) {
            ALERT_PROMPT.show();
            FirebaseAuth.AuthStateListener AUTH;
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FIREBASEAUTH.signOut();
            FirebaseAuth.getInstance().signOut();
            Intent backtologin;
            backtologin = new Intent(MainActivity.this, Login.class);
            startActivity(backtologin);
            finish();
            /*
            AUTH = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = FIREBASEAUTH.getCurrentUser();
                    if(user == null){
                        Intent backtologin;
                        backtologin = new Intent(MainActivity.this, Login.class);
                        startActivity(backtologin);
                        finish();
                    }
                }
            };
             */
            return true;
        }

        if (id == R.id.SwitchCamera) {
            if (CAMERA_VIEW.isFacingFront()) {
                CAMERA_VIEW.setFacing(CameraKit.Constants.FACING_BACK);
                Log.d(TAG, "FE" + "onclick():<> Camera is now facing the BACK ==> {ok} ");
            } else {
                CAMERA_VIEW.setFacing(CameraKit.Constants.FACING_FRONT);
                Log.d(TAG, "FE" + "onclick():<>  Camera is now facing the Front ==> {ok} ");
            }
            return true;
        }

        if (id == R.id.about) {
            new SweetAlertDialog(MainActivity.this)
                    .setTitleText("Version 1.0.0")
                    .setContentText("Developed by John Melody Melissa")
                    .show();
            return true;
        }

        if (id == R.id.freeze) {
            CAMERA_VIEW.start();
            CAMERA_VIEW.captureImage();
            GRAPHIC_OVERLAY.clear();
            return false;
        }

        if (id == R.id.Setting) {
            Intent SETTING;
            SETTING = new Intent(MainActivity.this, Preference.class);
            startActivity(SETTING);
            return true;
        }

        if (id == R.id.export){
            Intent export;
            export = new Intent(MainActivity.this, Data_list.class);
            startActivity(export);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ROTIBAKAR(String string) {
        Toast.makeText(MainActivity.this, string,
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onPause() {
        super.onPause();
        CAMERA_VIEW.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CAMERA_VIEW.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0x0; i < 0x3e8; i++) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }
                    });
                    try {
                        Thread.sleep(0x64);
                    } catch (InterruptedException e) {
                        Log.d(TAG, "FE" + e);
                    }
                }
            }
        }).start();
    }

    private void addEntry() {
        DATA.appendData(new DataPoint(TIME++, ran.nextDouble()), false
                , 0x64);
    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Sign Out? ")
                .setConfirmText("Yes.")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        ALERT_PROMPT.show();
                        FIREBASEAUTH.signOut();
                        System.exit(0x0);
                        sDialog.dismissWithAnimation();
                    }
                })
                .setCancelButton("No.", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                }).show();

    }
    
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onStart(){
        super.onStart();
        // TODO: firebase server;
    }
}