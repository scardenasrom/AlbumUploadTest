package testing.com.albumuploadtest.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.util.List;

import testing.com.albumuploadtest.R;
import testing.com.albumuploadtest.application.ZSApplication;
import testing.com.albumuploadtest.dto.PictureDto;
import testing.com.albumuploadtest.partials.PageOrderPic;
import testing.com.albumuploadtest.partials.SwappablePicture;
import testing.com.albumuploadtest.utils.TapTargetUtils;

public class OrderPicturesActivity extends ParentActivity {

    private Toolbar toolbar;
    private LinearLayout pagesContainer;

    private List<PictureDto> pictures;

    private View.OnClickListener onSwappablePictureClick;
    private boolean swappingPictures = false;

    private View backIcon;
    private View helpIcon;
    private View nextIcon;

    private SwappablePicture firstPicture;
    private SwappablePicture secondPicture;

    private TapTarget targetFirstPicture;
    private TapTarget targetSecondPicture;
    private TapTarget targetBack;
    private TapTarget targetNext;
    private TapTarget targetHelp;
    private TapTargetSequence tutorialFirstSequence;
    private TapTargetSequence tutorialSecondSequence;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pictures);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backIcon = aq.id(R.id.toolbar_back).getView();
        helpIcon = aq.id(R.id.toolbar_help).getView();
        nextIcon = aq.id(R.id.toolbar_next).getView();

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        helpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupTutorial();
                startTutorial();
            }
        });
        nextIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Continue to order page activity
            }
        });

        pagesContainer = (LinearLayout)findViewById(R.id.order_pictures_content);
        setupOnSwappablePictureClickListener();
        setupPages();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setupTutorial();
                if (!getOrderPicsTutorialPref())
                    startTutorial();
            }
        }, 500);
    }

    private void setupPages() {
        if (ZSApplication.getInstance().getAlbum().getTotalNumOfPics() == SMALL_ALBUM_NUM_OF_PICS) {
            setupSmallAlbum();
        } else if (ZSApplication.getInstance().getAlbum().getTotalNumOfPics() == LARGE_ALBUM_NUM_OF_PICS) {
            setupLargeAlbum();
        }
    }

    private void setupSmallAlbum() {
        pictures = ZSApplication.getInstance().getAlbum().getPictures();
        int pageIndex = 1;
        for (int i = 0; i < pictures.size(); i = i + 2) {
            PictureDto pic1 = pictures.get(i);
            PictureDto pic2 = pictures.get(i+1);
            SwappablePicture swappablePicture1 = new SwappablePicture(OrderPicturesActivity.this, pic1);
            SwappablePicture swappablePicture2 = new SwappablePicture(OrderPicturesActivity.this, pic2);
            swappablePicture1.setTag("pic" + i);
            swappablePicture2.setTag("pic" + (i+1));
            swappablePicture1.setOnClickListener(onSwappablePictureClick);
            swappablePicture2.setOnClickListener(onSwappablePictureClick);
            PageOrderPic pageOrderPic = new PageOrderPic(OrderPicturesActivity.this, "Página " + pageIndex);
            pageIndex++;
            pageOrderPic.addPictureToPage(swappablePicture1);
            pageOrderPic.addPictureToPage(swappablePicture2);
            pagesContainer.addView(pageOrderPic);
        }
    }

    private void setupLargeAlbum() {
        pictures = ZSApplication.getInstance().getAlbum().getPictures();
        int pageIndex = 1;
        for (int i = 0; i < pictures.size(); i = i + 4) {
            PictureDto pic1 = pictures.get(i);
            PictureDto pic2 = pictures.get(i+1);
            PictureDto pic3 = pictures.get(i+2);
            PictureDto pic4 = pictures.get(i+3);
            SwappablePicture swappablePicture1 = new SwappablePicture(OrderPicturesActivity.this, pic1);
            SwappablePicture swappablePicture2 = new SwappablePicture(OrderPicturesActivity.this, pic2);
            SwappablePicture swappablePicture3 = new SwappablePicture(OrderPicturesActivity.this, pic3);
            SwappablePicture swappablePicture4 = new SwappablePicture(OrderPicturesActivity.this, pic4);
            swappablePicture1.setTag("pic" + i);
            swappablePicture2.setTag("pic" + (i+1));
            swappablePicture3.setTag("pic" + (i+2));
            swappablePicture4.setTag("pic" + (i+3));
            swappablePicture1.setOnClickListener(onSwappablePictureClick);
            swappablePicture2.setOnClickListener(onSwappablePictureClick);
            swappablePicture3.setOnClickListener(onSwappablePictureClick);
            swappablePicture4.setOnClickListener(onSwappablePictureClick);
            PageOrderPic pageOrderPic = new PageOrderPic(OrderPicturesActivity.this, "Página " + pageIndex);
            pageIndex++;
            pageOrderPic.addPictureToPage(swappablePicture1);
            pageOrderPic.addPictureToPage(swappablePicture2);
            pageOrderPic.addPictureToPage(swappablePicture3);
            pageOrderPic.addPictureToPage(swappablePicture4);
            pagesContainer.addView(pageOrderPic);
        }
    }

    private void setupOnSwappablePictureClickListener() {
        onSwappablePictureClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwappablePicture clickedPicture = (SwappablePicture)view;
                if (!swappingPictures) {
                    swappingPictures = true;
                    clickedPicture.setSelected(true);
                    firstPicture = clickedPicture;
                } else if (clickedPicture.isSelected()) {
                    swappingPictures = false;
                    clickedPicture.setSelected(false);
                    firstPicture = null;
                } else if (!clickedPicture.isSelected()) {
                    swappingPictures = false;
                    clickedPicture.setSelected(true);
                    secondPicture = clickedPicture;
                    swapPictures();
                }
            }
        };
    }

    private void swapPictures() {
        firstPicture.setSelected(false);
        secondPicture.setSelected(false);
        PictureDto firstPic = firstPicture.getPicture();
        PictureDto secondPic = secondPicture.getPicture();
        firstPicture.setPicture(secondPic);
        secondPicture.setPicture(firstPic);
        ZSApplication.getInstance().swapPictures(firstPic, secondPic);
    }

    private void showTutorialDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderPicturesActivity.this)
                .setTitle("Cómo mover fotos")
                .setMessage("Puedes mover las fotos de sitio, intercambiando la posición entre dos fotos. Pulsa sobre una foto y esta se marcará con un borde. Luego pulsa sobre la otra foto y ambas intercambiarán posiciones.")
                .setCancelable(false)
                .setNegativeButton("No volver a mostrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        setOrderPicsTutorialPref(true);
                    }
                })
                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setupTutorial() {
        targetFirstPicture = TapTargetUtils.getTapTarget(pagesContainer.findViewWithTag("pic0"), "Cómo mover fotos", "Para cambiar la posición de dos fotografías, primero pulsa sobre una fotografía, su borde se volverá de color rojo.");
        targetSecondPicture = TapTargetUtils.getTapTarget(pagesContainer.findViewWithTag("pic3"), "Cómo mover fotos", "A continuación pulsa sobre otra, y las fotografías intercambiarán posiciones.");
        targetBack = TapTargetUtils.getTapTarget(backIcon, "Volver a selección", "Pulsa atrás para volver a seleccionar las fotos");
        targetNext = TapTargetUtils.getTapTarget(nextIcon, "Seguir", "Pulsa en Seguir para continuar con la edición del álbum.");
        targetHelp = TapTargetUtils.getTapTarget(helpIcon, "Ayuda", "Pulsa en el icono de ayuda para volver a mostrar esta explicación");
        tutorialFirstSequence = new TapTargetSequence(OrderPicturesActivity.this)
                .targets(targetFirstPicture, targetSecondPicture)
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tutorialSecondSequence.start();
                            }
                        }, 500);
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget) {
                        if (lastTarget == targetFirstPicture) {
                            swappingPictures = true;
                            SwappablePicture clickedPicture = (SwappablePicture)pagesContainer.findViewWithTag("pic0");
                            clickedPicture.setSelected(true);
                            firstPicture = clickedPicture;
                        } else if (lastTarget == targetSecondPicture) {
                            swappingPictures = false;
                            SwappablePicture clickedPicture = (SwappablePicture)pagesContainer.findViewWithTag("pic3");
                            clickedPicture.setSelected(true);
                            secondPicture = clickedPicture;
                            swapPictures();
                        }
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                });
        tutorialSecondSequence = new TapTargetSequence(OrderPicturesActivity.this)
                .targets(targetBack, targetNext, targetHelp)
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        setOrderPicsTutorialPref(true);
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                });
    }

    public void startTutorial() {
        tutorialFirstSequence.start();
    }

}
