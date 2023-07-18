package com.example.bookmangerjava.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookmangerjava.R;
import com.example.bookmangerjava.constant.ApiCallback;
import com.example.bookmangerjava.controller.ApiController;
import com.example.bookmangerjava.databinding.ActivityKindOfBookBinding;
import com.example.bookmangerjava.model.KindOfBook;
import com.example.bookmangerjava.model.response.BodyKindOfBookResponse;
import com.example.bookmangerjava.model.response.BodyResponseAddKindOfBook;
import com.example.bookmangerjava.ui.adapter.KindOfBookAdapter;

import java.util.List;

public class KindOfBookActivity extends AppCompatActivity implements KindOfBookAdapter.Callback {
    private ActivityKindOfBookBinding binding;
    private ApiController apiController;
    private KindOfBookAdapter kindOfBookAdapter;
    private List<KindOfBook> kindOfBookList;
    private TextView titleThemLoai;
    private EditText name;
    private Button add;
    private TextView exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKindOfBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();

        kindOfBookAdapter = new KindOfBookAdapter(this);
        binding.listKindOfBook.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.listKindOfBook.setAdapter(kindOfBookAdapter);
        apiController = new ApiController();
        apiController.getAllListKindOfBook(new ApiCallback<BodyKindOfBookResponse>() {
            @Override
            public void onSuccess(BodyKindOfBookResponse data) {
                binding.progressBar.setVisibility(View.GONE);
                kindOfBookList = data.getData();
                kindOfBookAdapter.setData(kindOfBookList);
                binding.count.setText("There are " + kindOfBookList.size() + " kinds of books");
            }

            @Override
            public void onError(Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        binding.add.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(this);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialogaddkindofbook);
            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (dialog != null && dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            titleThemLoai = (TextView) dialog.findViewById(R.id.titleThemLoai);
            name = (EditText) dialog.findViewById(R.id.name);
            add = (Button) dialog.findViewById(R.id.add);
            exit = (TextView) dialog.findViewById(R.id.exit);

            add.setOnClickListener(view2 -> {
                String themText = name.getText().toString();
                if (themText.isEmpty() || themText.toString() == null) {
                    Toast.makeText(this, "Cannot be left blank", Toast.LENGTH_SHORT).show();
                } else {
                    KindOfBook kindOfBook = new KindOfBook(name.getText().toString());
                    apiController.addKindOfBook(kindOfBook, new ApiCallback<BodyResponseAddKindOfBook>() {
                        @Override
                        public void onSuccess(BodyResponseAddKindOfBook data) {
                            if (data.getMessage().isStatus()) {
                                Toast.makeText(KindOfBookActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                                kindOfBookList.add(data.getData());
                                binding.count.setText("There are " + kindOfBookList.size() + " kinds of books");
                                dialog.cancel();
                            } else {
                                Toast.makeText(KindOfBookActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            Toast.makeText(KindOfBookActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                }
            });

            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onEdit(KindOfBook kindOfBook, int position) {


        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogaddkindofbook);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        titleThemLoai = (TextView) dialog.findViewById(R.id.titleThemLoai);
        name = (EditText) dialog.findViewById(R.id.name);
        add = (Button) dialog.findViewById(R.id.add);
        exit = (TextView) dialog.findViewById(R.id.exit);
        titleThemLoai.setText("Edit Kindofbook");
        add.setText("Edit");
        name.setText(kindOfBook.getName());
        add.setOnClickListener(view2 -> {
            String themText = name.getText().toString();
            if (themText.isEmpty() || themText.toString() == null) {
                Toast.makeText(this, "Cannot be left blank", Toast.LENGTH_SHORT).show();
            } else {
                KindOfBook kindOfBook1 = new KindOfBook(kindOfBook.get_id(), name.getText().toString());
                apiController.updateKindOfBook(kindOfBook.get_id(), kindOfBook1, new ApiCallback<BodyResponseAddKindOfBook>() {
                    @Override
                    public void onSuccess(BodyResponseAddKindOfBook data) {
                        if (data.getMessage().isStatus()) {
                            Toast.makeText(KindOfBookActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                            kindOfBookList.set(position, kindOfBook1);
                            kindOfBookAdapter.notifyItemChanged(position);
                            dialog.cancel();
                        } else {
                            Toast.makeText(KindOfBookActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(KindOfBookActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public void onDelete(KindOfBook kindOfBook, int position) {

        Log.e("MinhCheck", kindOfBook.get_id());

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Notification").setMessage("\n" + "Are you sure you want to remove").setPositiveButton("Ok", null).setNegativeButton("Cancel", null).show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiController.deleteKindOfBook(kindOfBook.get_id(), new ApiCallback<BodyResponseAddKindOfBook>() {
                    @Override
                    public void onSuccess(BodyResponseAddKindOfBook data) {
                        if (data.getMessage().isStatus()) {
                            Toast.makeText(KindOfBookActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                            kindOfBookList.remove(kindOfBook);
                            kindOfBookAdapter.notifyItemRemoved(position);
                            binding.count.setText("There are " + kindOfBookList.size() + " kinds of books");
                        } else {
                            Toast.makeText(KindOfBookActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(KindOfBookActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.cancel();
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Kind of book");
        }
    }
}