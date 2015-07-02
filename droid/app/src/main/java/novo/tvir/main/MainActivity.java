package novo.tvir.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import com.j256.ormlite.dao.Dao;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.signin.activity.SignInActivity_;
import novo.tvir.access.signup.activity.SignUpActivity_;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.ViewById;
import persist.DBHelper;
import persist.domain.Account;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// @author: Mykhaylo Titov on 22.05.15 19:13.
@Slf4j
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity{

    @ViewById(R.id.toolbar) Toolbar toolbar;

    @OrmLiteDao(helper = DBHelper.class) Dao<Account, Integer> accountDao;

    Drawer drawer;

    @AfterViews
    public void onAfterView(){

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayList<IDrawerItem> drawerItems = new ArrayList<>();

        ArrayList<IProfile> profiles = new ArrayList<>();
        try {
            List<Account> accounts = accountDao.queryForAll();
            for (Account account : accounts) {
                ProfileDrawerItem profileDrawerItem = new ProfileDrawerItem();
                profileDrawerItem.setEmail(account.getEmail());
                profileDrawerItem.setName(account.getName());
                profileDrawerItem.setEnabled(account.isActivated() && !account.isBlocked());
                profiles.add(profileDrawerItem);
            }
        } catch (SQLException e) {
            log.error("Can't get accounts", e);
        }

        final PrimaryDrawerItem signUpDrawerItem = new PrimaryDrawerItem().withName(R.string.nav_menu_item_sign_up);
        final SecondaryDrawerItem signInDrawerItem = new SecondaryDrawerItem().withName(R.string.nav_menu_item_sign_in);

        drawerItems.add(signUpDrawerItem);
        drawerItems.add(signInDrawerItem);

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withProfiles(profiles)
                .build();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(accountHeader)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerItems(drawerItems)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Intent intent = null;
                        if(iDrawerItem == signUpDrawerItem){
                            intent = new Intent(MainActivity.this, SignUpActivity_.class);

                        }else if (iDrawerItem == signInDrawerItem){
                            intent = new Intent(MainActivity.this, SignInActivity_.class);
                        }
                        startActivity(intent);
                        return false;
                    }
                })
                .build();

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen()){
            drawer.closeDrawer();
        }else {
            super.onBackPressed();
        }
    }
}
