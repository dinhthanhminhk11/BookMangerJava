package com.example.bookmangerjava.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookmangerjava.MainActivity;
import com.example.bookmangerjava.R;
import com.example.bookmangerjava.constant.ApiCallback;
import com.example.bookmangerjava.controller.ApiController;
import com.example.bookmangerjava.databinding.ActivityBookBinding;
import com.example.bookmangerjava.model.Book;
import com.example.bookmangerjava.model.KindOfBook;
import com.example.bookmangerjava.model.response.BodyBookResponse;
import com.example.bookmangerjava.model.response.BodyKindOfBookResponse;
import com.example.bookmangerjava.model.response.BodyResponseAddBook;
import com.example.bookmangerjava.ui.adapter.BookAdapter;
import com.example.bookmangerjava.ui.adapter.CustomKindOfBookAdapter;

import java.util.List;

public class BookActivity extends AppCompatActivity implements BookAdapter.Callback {
    private ActivityBookBinding binding;
    private ApiController apiController;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private TextView titleAddBook;
    private EditText name;
    private EditText price;
    private EditText discount;
    private Spinner listKindOfBook;
    private Button cancel;
    private Button add;
    private String idCategory = "";
    private String nameCategory = "";
    private List<KindOfBook> kindOfBookList;
    private CustomKindOfBookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        bookAdapter = new BookAdapter(this);
        binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerview.setAdapter(bookAdapter);

        apiController = new ApiController();
        apiController.getListBook(new ApiCallback<BodyBookResponse>() {
            @Override
            public void onSuccess(BodyBookResponse data) {
                binding.progressBar.setVisibility(View.GONE);
                bookList = data.getData();
                bookAdapter.setData(bookList);
                binding.count.setText("There are " + bookList.size() + " books");
            }

            @Override
            public void onError(Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        apiController.getAllListKindOfBook(new ApiCallback<BodyKindOfBookResponse>() {
            @Override
            public void onSuccess(BodyKindOfBookResponse data) {
                binding.progressBar.setVisibility(View.GONE);
                kindOfBookList = data.getData();
            }

            @Override
            public void onError(Throwable t) {

            }
        });

        binding.btnAddKindofbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(BookActivity.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialogaddbook);
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (dialog != null && dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }

                titleAddBook = (TextView) dialog.findViewById(R.id.titleAddBook);
                name = (EditText) dialog.findViewById(R.id.name);
                price = (EditText) dialog.findViewById(R.id.price);
                discount = (EditText) dialog.findViewById(R.id.discount);
                listKindOfBook = (Spinner) dialog.findViewById(R.id.listKindOfBook);
                cancel = (Button) dialog.findViewById(R.id.cancel);
                add = (Button) dialog.findViewById(R.id.add);

                initSpinner();
                add.setOnClickListener(view3 -> {
                    if (name.getText().toString().isEmpty() || name.getText().toString() == null || String.valueOf(price.getText().toString()).isEmpty() || String.valueOf(price.getText().toString()) == null || String.valueOf(discount.getText().toString()).isEmpty() || String.valueOf(discount.getText().toString()) == null) {
                        Toast.makeText(BookActivity.this, "Cannot be left blank", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(discount.getText().toString().trim()) > 100 || Integer.parseInt(discount.getText().toString().trim()) < 0) {
                        Toast.makeText(BookActivity.this, "Discount only from 0 to 100", Toast.LENGTH_SHORT).show();
                    } else {
                        Book book = new Book(name.getText().toString(), Integer.parseInt(price.getText().toString()), Integer.parseInt(discount.getText().toString()), idCategory);
                        apiController.addBook(book, new ApiCallback<BodyResponseAddBook>() {
                            @Override
                            public void onSuccess(BodyResponseAddBook data) {
                                if (data.getMessage().isStatus()) {
                                    Toast.makeText(BookActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                                    bookAdapter.addItem(new Book(data.getData().getName(), data.getData().getPrice(), data.getData().getDiscount(), data.getData().getIdCategory(), nameCategory, data.getData().get_id()));
                                    binding.count.setText("There are " + bookList.size() + " books");
                                    dialog.cancel();
                                } else {
                                    Toast.makeText(BookActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(BookActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchQuery = editable.toString().trim();
                bookAdapter.setSearchQuery(searchQuery);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onEdit(Book book, int position) {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogaddbook);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        titleAddBook = (TextView) dialog.findViewById(R.id.titleAddBook);
        name = (EditText) dialog.findViewById(R.id.name);
        price = (EditText) dialog.findViewById(R.id.price);
        discount = (EditText) dialog.findViewById(R.id.discount);
        listKindOfBook = (Spinner) dialog.findViewById(R.id.listKindOfBook);
        cancel = (Button) dialog.findViewById(R.id.cancel);
        add = (Button) dialog.findViewById(R.id.add);
        titleAddBook.setText("Edit Book");
        add.setText("Submit");
        name.setText(book.getName());
        price.setText(String.valueOf(book.getPrice()));
        discount.setText(String.valueOf(book.getDiscount()));


        initSpinner();
        Log.e("MInh", book.getNameCategory());

        int positionToSelect = -1;

        for (int i = 0; i < kindOfBookList.size(); i++) {
            KindOfBook kindOfBook = kindOfBookList.get(i);
            if (kindOfBook.getName().equals(book.getNameCategory())) {
                positionToSelect = i;
                break;
            }
        }

        if (positionToSelect != -1) {
            this.listKindOfBook.setSelection(positionToSelect);
        }

        add.setOnClickListener(view2 -> {
            if (name.getText().toString().isEmpty() || name.getText().toString() == null || String.valueOf(price.getText().toString()).isEmpty() || String.valueOf(price.getText().toString()) == null || String.valueOf(discount.getText().toString()).isEmpty() || String.valueOf(discount.getText().toString()) == null) {
                Toast.makeText(BookActivity.this, "Cannot be left blank", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(discount.getText().toString().trim()) > 100 || Integer.parseInt(discount.getText().toString().trim()) < 0) {
                Toast.makeText(BookActivity.this, "Discount only from 0 to 100", Toast.LENGTH_SHORT).show();
            } else {
                Book bookRequest = new Book(name.getText().toString(), Integer.parseInt(price.getText().toString()), Integer.parseInt(discount.getText().toString()), idCategory);
                apiController.updateBook(book.get_id(), bookRequest, new ApiCallback<BodyResponseAddBook>() {
                    @Override
                    public void onSuccess(BodyResponseAddBook data) {
                        if (data.getMessage().isStatus()) {
                            Toast.makeText(BookActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                            bookAdapter.updateItem(new Book(bookRequest.getName(), bookRequest.getPrice(), bookRequest.getDiscount(), bookRequest.getIdCategory(), nameCategory, book.get_id()),position);
                            dialog.cancel();
                        } else {
                            Toast.makeText(BookActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(BookActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onDelete(Book book, int position) {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Notification").setMessage("Are you sure you want to remove").setPositiveButton("Ok", null).setNegativeButton("Cancel", null).show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiController.deleteBook(book.get_id(), new ApiCallback<BodyResponseAddBook>() {
                    @Override
                    public void onSuccess(BodyResponseAddBook data) {
                        if (data.getMessage().isStatus()) {
                            Toast.makeText(BookActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                            bookAdapter.deleteItem(position);
                            binding.count.setText("There are " + bookList.size() + " books");
                        } else {
                            Toast.makeText(BookActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(BookActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.cancel();
            }
        });
    }

    @Override
    public void onAdd(Book book, int position) {
        startActivity(new Intent(BookActivity.this, AddLoanSlipActivity.class));
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Book Manager");
        }
    }

    private void initSpinner() {
        adapter = new CustomKindOfBookAdapter(BookActivity.this, kindOfBookList);
        this.listKindOfBook.setAdapter(adapter);

        this.listKindOfBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                KindOfBook selectedKindOfBook = adapter.getItem(position);
                if (selectedKindOfBook != null) {
                    idCategory = selectedKindOfBook.get_id();
                    nameCategory = selectedKindOfBook.getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}