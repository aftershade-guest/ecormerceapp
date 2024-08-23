package com.example.ecommerceapp.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ecommerceapp.Model.Products;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SearchActivity extends AppCompatActivity {

    private Button SearchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String SearchInput;
    private SearchView searchView;
    private ImageButton intentSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        try {
            SearchInput = " ";

            searchView = findViewById(R.id.search_product_items);
            searchView.setOnQueryTextListener(search_items_product);
            intentSearch = findViewById(R.id.search_intent);

            searchList = findViewById(R.id.search_list);
            searchList.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

            intentSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage() + "\n" + e.getCause(), Toast.LENGTH_SHORT).show();
        }



//        inputText = findViewById(R.id.search_product_name);
//        SearchBtn = findViewById(R.id.search_btn);
//
//
//        SearchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SearchInput = inputText.getText().toString();
//                onStart();
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        try{
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

            FirebaseRecyclerOptions<Products> options =
                    new FirebaseRecyclerOptions.Builder<Products>()
                            .setQuery(reference.orderByChild("pname").startAt(SearchInput), Products.class)
                            .build();

            FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final Products products) {
                            productViewHolder.txtProductName.setText(products.getPname());
                            productViewHolder.txtProductDescription.setText(products.getDescription());
                            productViewHolder.txtProductPrice.setText("Price " + products.getPrice() + "$");
                            Picasso.get().load(products.getImage()).into(productViewHolder.imageView);

                            productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(SearchActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("pid", products.getPid());
                                    startActivity(intent);
                                }
                            });
                        }

                        @NonNull
                        @Override
                        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.products_items_layout, parent, false);

                            ProductViewHolder holder = new ProductViewHolder(view);
                            return holder;
                        }
                    };

            searchList.setAdapter(adapter);
            adapter.startListening();
        } catch (Exception e){
            Toast.makeText(SearchActivity.this, e.getMessage() + "\n" + e.getCause(), Toast.LENGTH_SHORT).show();
        }


    }

    private SearchView.OnQueryTextListener search_items_product = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String query) {
            try {

                SearchInput = query;
                onStart();
            }catch (Exception ex){
                Toast.makeText(SearchActivity.this, ex.getMessage() + "\n" + ex.getCause(), Toast.LENGTH_SHORT).show();
            }

            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

}