package com.buaa.jj.matrixcalculator;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import Jama.Matrix;

public class MatrixCalculatorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder=((ApplicationMainService.MyBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    private ApplicationMainService binder;
    private Context matrixCalculatorActivity=this;
    private Fragment currentFragment;
    private ListView drawer_listView;
    private int Matrix_id;
    private int fragment_status=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_calculator_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bindService(new Intent(MatrixCalculatorActivity.this,ApplicationMainService.class),conn,BIND_AUTO_CREATE);
        drawer_listView =(ListView) findViewById(R.id.matrix_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.menu);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final PopupMenu popupMenu=new PopupMenu(matrixCalculatorActivity,view);
                if(fragment_status==0)
                    popupMenu.getMenuInflater().inflate(R.menu.matrix_calculator_drawer,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();

                        if (id == R.id.analyse_matrix) {
                            Intent intent=new Intent(matrixCalculatorActivity,MatrixAnalyseActivity.class);
                            intent.putExtra("matrix",binder.getMList().get(Matrix_id));
                            startActivity(intent);
                        } else if (id == R.id.help) {
                            AlertDialog.Builder help=new AlertDialog.Builder(matrixCalculatorActivity);
                            help.setTitle("Help");
                            help.setMessage(R.string.Help);
                            help.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            help.setCancelable(false);
                            help.show();
                        } else if (id == R.id.about) {
                            AlertDialog.Builder about=new AlertDialog.Builder(matrixCalculatorActivity);
                            about.setTitle("About");
                            about.setMessage(R.string.About);
                            about.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            about.setCancelable(false);
                            about.show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                this.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                MatrixListAdapter matrixListAdapter =new MatrixListAdapter(binder.getMList(), matrixCalculatorActivity);
                drawer_listView.setAdapter(matrixListAdapter);
                drawer_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Matrix_id =i;
                        MyMatrix tmp=binder.getMList().get(i);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("MyMatrix",tmp);
                        currentFragment =new MatrixFragment();
                        currentFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.fragment_place, currentFragment).commit();
                    }
                });
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        ImageView image=(ImageView) findViewById(R.id.imageView);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(matrixCalculatorActivity,MatrixManagerActivity.class);
                startActivity(intent);
            }
        });

        Button button=(Button) findViewById(R.id.calculate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spinner1 = (Spinner) findViewById(R.id.matrix1);
                Spinner spinner2 = (Spinner) findViewById(R.id.matrix2);
                Spinner operation = (Spinner) findViewById(R.id.operate);
                MyMatrix myMatrix1 = (MyMatrix) spinner1.getSelectedItem();
                MyMatrix myMatrix2 = (MyMatrix) spinner2.getSelectedItem();
                String operate=(String) operation.getSelectedItem();
                if (myMatrix1 != null && myMatrix2 != null && operate!=null) {
                    if(operate.equals("Multiply")){
                        if (myMatrix1.getColumn() == myMatrix2.getRow()) {
                            Matrix matrix1 = new Matrix(myMatrix1.getNum());
                            Matrix ans = matrix1.times(new Matrix(myMatrix2.getNum()));
                            MyMatrix myAns = new MyMatrix(myMatrix1.getRow(), myMatrix2.getColumn(), ans.getArray(), "");
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("MyMatrix", myAns);
                            currentFragment = new MatrixFragment();
                            currentFragment.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.fragment_place, currentFragment).commit();
                        }else {
                            Toast.makeText(matrixCalculatorActivity,"The column of left matrix not equal to the row of right matrix!",Toast.LENGTH_LONG).show();
                        }
                    }else if (operate.equals("Plus")){
                        if(myMatrix1.getColumn()==myMatrix2.getColumn()&&myMatrix1.getRow()==myMatrix2.getRow()){
                            Matrix matrix1 = new Matrix(myMatrix1.getNum());
                            Matrix ans = matrix1.plus(new Matrix(myMatrix2.getNum()));
                            MyMatrix myAns = new MyMatrix(myMatrix1.getRow(), myMatrix1.getColumn(), ans.getArray(), "");
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("MyMatrix", myAns);
                            currentFragment = new MatrixFragment();
                            currentFragment.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.fragment_place, currentFragment).commit();
                        }else {
                            Toast.makeText(matrixCalculatorActivity,"The column and row of left matrix not equal to those of right matrix!",Toast.LENGTH_LONG).show();
                        }
                    }else if(operate.equals("Minus")){
                        if(myMatrix1.getColumn()==myMatrix2.getColumn()&&myMatrix1.getRow()==myMatrix2.getRow()){
                            Matrix matrix1 = new Matrix(myMatrix1.getNum());
                            Matrix ans = matrix1.minus(new Matrix(myMatrix2.getNum()));
                            MyMatrix myAns = new MyMatrix(myMatrix1.getRow(), myMatrix1.getColumn(), ans.getArray(), "");
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("MyMatrix", myAns);
                            currentFragment = new MatrixFragment();
                            currentFragment.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.fragment_place, currentFragment).commit();
                        }else {
                            Toast.makeText(matrixCalculatorActivity,"The column and row of left matrix not equal to those of right matrix!",Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else {
                    Toast.makeText(matrixCalculatorActivity,"Please select the items",Toast.LENGTH_LONG).show();
                }
            }
        });

        button=(Button) findViewById(R.id.refresh_spinner);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spinner=(Spinner) findViewById(R.id.matrix1);
                spinner.setAdapter(new MatrixListAdapter(binder.getMList(),matrixCalculatorActivity));
                spinner=(Spinner) findViewById(R.id.matrix2);
                spinner.setAdapter(new MatrixListAdapter(binder.getMList(),matrixCalculatorActivity));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.matrix_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
