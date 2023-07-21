package com.example.bookmangerjava.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookmangerjava.R;
import com.example.bookmangerjava.constant.ApiCallback;
import com.example.bookmangerjava.constant.AppConstant;
import com.example.bookmangerjava.controller.ApiController;
import com.example.bookmangerjava.databinding.ActivityLibrarianBinding;
import com.example.bookmangerjava.model.User;
import com.example.bookmangerjava.model.UserClient;
import com.example.bookmangerjava.model.response.BodyLoginResponse;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;

public class LibrarianActivity extends AppCompatActivity {

    private ActivityLibrarianBinding binding;
    public static final int CAMERA_PERMISSION_REQ = 100;
    private Dialog dialog;
    private Uri imagePath = null;
    private ApiController apiController;
    private int type = 0;
    private String dinhDangSaiVeHo = "((?=.*[a-zA-Z])(?=.*[0-9]).{1,20})";
    private String userNameCaptcha = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLibrarianBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        apiController = new ApiController();
        binding.chonanh.setOnClickListener(v -> ImagePicker.with(LibrarianActivity.this).crop().compress(1024).maxResultSize(1080, 1080).start(20));

        binding.btDangKi.setOnClickListener(v -> {
            if (type == 0) {
                if (binding.etUsernameRegister.getText().toString().isEmpty() || binding.etUsernameRegister.getText().toString() == null) {
                    binding.tvghichu.setText("You Have Not Entered Your Username");
                    binding.tvghichu.setTextColor(Color.RED);
                } else if (binding.etmatkhaumoi.getText().toString().isEmpty() || binding.etmatkhaumoi.getText().toString() == null) {
                    binding.tvghichu.setText("You Have Not Entered Password");
                    binding.tvghichu.setTextColor(Color.RED);
                } else if (binding.nhaplaimatkhaumoi.getText().toString().isEmpty() || binding.nhaplaimatkhaumoi.getText().toString() == null) {
                    binding.tvghichu.setText("You Have Not Re-entered Password");
                    binding.tvghichu.setTextColor(Color.RED);
                } else if (binding.etHoten.getText().toString().isEmpty() || binding.etHoten.getText().toString() == null) {
                    binding.tvghichu.setText("You Have Not Entered Your Name");
                    binding.tvghichu.setTextColor(Color.RED);
                } else if (binding.etmatkhaumoi.getText().toString().equals(binding.nhaplaimatkhaumoi.getText().toString()) == false) {
                    binding.tvghichu.setText("Re-enter Password Do Not Duplicate");
                    binding.tvghichu.setTextColor(Color.RED);
                    binding.etmatkhaumoi.setText("");
                    binding.nhaplaimatkhaumoi.setText("");
                } else if (imagePath == null) {
                    binding.tvghichu.setText("Please choose an invalid photo again");
                    Toast.makeText(LibrarianActivity.this, "Your picture is too heavy, I give up", Toast.LENGTH_SHORT).show();
                    binding.tvghichu.setTextColor(Color.RED);
                } else if (binding.etHoten.getText().toString().matches("^[A-Z].*") == false) {
                    binding.etHoten.setError("First Name Must Capitalize");
                } else if (binding.etHoten.getText().toString().matches(dinhDangSaiVeHo) == true) {
                    binding.etHoten.setError("Name No number");
                } else if (binding.etHoten.getText().toString().length() < 5) {
                    binding.etHoten.setError("Username must be at least 5 . long");
                } else if (binding.etHoten.getText().toString().length() > 15) {
                    binding.etHoten.setError("Username must be up to 15 . in length");
                } else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.btDangKi.setEnabled(false);
                    apiController.register(binding.etHoten.getText().toString(), binding.etUsernameRegister.getText().toString(), binding.nhaplaimatkhaumoi.getText().toString(), new File(getRealPathFromURI(imagePath)), new ApiCallback<BodyLoginResponse>() {
                        @Override
                        public void onSuccess(BodyLoginResponse data) {
                            binding.progressBar.setVisibility(View.GONE);
                            binding.btDangKi.setEnabled(true);
                            Toast.makeText(LibrarianActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                            if (data.getMessage().isStatus()) {
                                finish();
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            binding.progressBar.setVisibility(View.GONE);
                            binding.btDangKi.setEnabled(true);
                            Toast.makeText(LibrarianActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                if (binding.etHoten.getText().toString().isEmpty() || binding.etHoten.getText().toString() == null) {
                    binding.tvghichu.setText("You Have Not Entered Your Name");
                    binding.tvghichu.setTextColor(Color.RED);
                } else if (binding.etHoten.getText().toString().matches("^[A-Z].*") == false) {
                    binding.etHoten.setError("First Name Must Capitalize");
                } else if (binding.etHoten.getText().toString().matches(dinhDangSaiVeHo) == true) {
                    binding.etHoten.setError("Name No number");
                } else if (binding.etHoten.getText().toString().length() < 5) {
                    binding.etHoten.setError("Username must be at least 5 . long");
                } else if (binding.etHoten.getText().toString().length() > 15) {
                    binding.etHoten.setError("Username must be up to 15 . in length");
                } else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.btDangKi.setEnabled(false);
                    apiController.updateUser(binding.etHoten.getText().toString(), userNameCaptcha, imagePath == null ? null : new File(getRealPathFromURI(imagePath)), new ApiCallback<BodyLoginResponse>() {
                        @Override
                        public void onSuccess(BodyLoginResponse data) {
                            binding.progressBar.setVisibility(View.GONE);
                            binding.btDangKi.setEnabled(true);
                            Toast.makeText(LibrarianActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                            if (data.getMessage().isStatus()) {
                                if (UserClient.getInstance().getId().equals(data.getData().getId())) {
                                    UserClient.getInstance().setImage(data.getData().getImage());
                                    UserClient.getInstance().setFullName(data.getData().getFullName());
                                }
                                finish();
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            binding.progressBar.setVisibility(View.GONE);
                            binding.btDangKi.setEnabled(true);
                            Toast.makeText(LibrarianActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQ) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                    if (showRationale) {
                        Toast.makeText(this, "Đã từ chối quyền máy ảnh", Toast.LENGTH_SHORT).show();
                    } else {
                        showSettingsAlert();
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePath = data.getData();
        binding.idImageUser.setImageURI(imagePath);
    }

    @SuppressLint({"ObsoleteSdkInt", "UseCompatLoadingForDrawables"})
    private void showSettingsAlert() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.permission_camera_editprofile_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_dialog_editprofile));
        }
        dialog.setCancelable(true);
        Button btnSetting = dialog.findViewById(R.id.btnSettingDialogEditProfile);
        Button btnCancel = dialog.findViewById(R.id.btnCancelDialogEditProfile);
        btnSetting.setOnClickListener(view -> {
            dialog.dismiss();
            openAppSetting(this);
        });

        btnCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void openAppSetting(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

        }
        User user = (User) getIntent().getSerializableExtra(AppConstant.USER);
        if (user != null) {
            getSupportActionBar().setTitle("Edit Librarian");
            type = 1;
            binding.etHoten.setText(user.getFullName());
            binding.btDangKi.setText("Submit");
            binding.createTaiKhoan.setText("Update Librarian");
            binding.nhaplaimatkhaumoi.setVisibility(View.GONE);
            binding.etUsernameRegister.setVisibility(View.GONE);
            binding.etmatkhaumoi.setVisibility(View.GONE);
            userNameCaptcha = user.getUsername();
            RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.book2).error(R.drawable.book2);
            Glide.with(binding.idImageUser.getContext()).load(AppConstant.BASE + "/uploads/" + user.getImage()).apply(options).into(binding.idImageUser);
        } else {
            getSupportActionBar().setTitle("Add Librarian");
        }

    }

    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) {
            return uri.getPath();
        } else {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }
    }
}