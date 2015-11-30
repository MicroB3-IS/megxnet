# Agreed upon way to work with the MegxNet Git repository
## Central workflow

Like Subversion, the Centralized Workflow uses a central repository to serve as the single point-of-entry for all changes to the project. Instead of **trunk**, the default development branch is called **master** and all changes are committed into this branch. This workflow doesn’t require any other branches besides **master**.

Each developer creates a local copy of the entire project. This is accomplished via the `git clone` command:

```
git clone git@github.com:MicroB3-IS/megxnet.git
```

When you clone a repository, Git automatically adds a shortcut called **origin** that points back to the **“parent”** repository, under the assumption that you'll want to interact with it further on down the road.

In your local repository, you can develop features using the standard Git commit process: **edit**, **stage**, and **commit**. If you’re not familiar with the **staging area**, it’s a way to prepare a commit without having to include every change existing in the local working directory. This lets you create highly focused commits, even if you’ve made a lot of local changes.

```
git status # View the state of the repo
git add <some-file> # Stage a file
git commit # Commit a file</some-file>
```

Once you finish your feature, he should publish his local commits to the central repository so other team members can access it. He can do this with the `git push` command, like so:

```
git push origin master
```

Remember that **origin** is the remote connection to the central repository that Git created when you cloned it. The **master** argument tells Git to try to make the origin’s master branch look like his local master branch

Let’s see what happens if someone else tries to push his/her feature after you've successfully published your changes to the central repository. He/she can use the exact same **push** command:

```
git push origin master
```

But, since his\her local history has diverged from the central repository, Git will refuse the request with a rather verbose error message:

```
error: failed to push some refs to '/path/to/repo.git'
hint: Updates were rejected because the tip of your current branch is behind
hint: its remote counterpart. Merge the remote changes (e.g. 'git pull')
hint: before pushing again.
hint: See the 'Note about fast-forwards' in 'git push --help' for details.
```

This prevents the other developer from overwriting official commits. He/she needs to pull your updates into his/her repository, integrate them with his/her local changes, and then try again.

He/she can use `git pull` to incorporate upstream changes into her repository. This command is sort of like `svn update`—it pulls the entire upstream commit history into the other developer's local repository and tries to integrate it with his/her local commits:

```
git pull --rebase origin master
```

The `--rebase` option tells Git to move all of the other developer's commits to the tip of the master branch after synchronising it with the changes from the central repository

If developers are working on unrelated features, it’s unlikely that the **rebasing** process will generate conflicts. But if it does, Git will pause the **rebase** at the current commit and output the following message, along with some relevant instructions:

```
CONFLICT (content): Merge conflict in <some-file>
```

The great thing about Git is that anyone can resolve their own merge conflicts. In our example, one would simply run a git status to see where the problem is. Conflicted files will appear in the **Unmerged** paths section:

```
# Unmerged paths:
# (use "git reset HEAD <some-file>..." to unstage)
# (use "git add/rm <some-file>..." as appropriate to mark resolution)
#
# both modified: <some-file>
```

Then, he/she’ll edit the file(s) to his/her liking. Once he/she’s happy with the result, he/she can stage the file(s) in the usual fashion and let git rebase do the rest:

```
git add <some-file>
git rebase --continue
```

And that’s all there is to it. Git will move on to the next commit and repeat the process for any other commits that generate conflicts.

If you get to this point and realize and you have no idea what’s going on, don’t panic. Just execute the following command and you’ll be right back to where you started before you ran `[git pull --rebase](/tutorials/syncing/git-pull)`:

```
git rebase --abort
```

However, for MegxNet development purposes we're going to use a wokflow pattern named **Feature Branch Workflow**. 

The core idea behind the **Feature Branch Workflow** is that all feature development should take place in a dedicated branch instead of the master branch. This encapsulation makes it easy for multiple developers to work on a **particular** feature without disturbing the **main** codebase. It also means the master branch will never contain **broken** code, which is a huge advantage for continuous integration environments.

Instead of committing directly on their local master branch, developers create a new branch every time they start work on a new feature. Feature branches should have descriptive names, like _animated-menu-items_ or _issue-#1061_. The idea is to give a clear, highly-focused purpose to each branch.

Once someone completes a feature, they don’t immediately merge it into master. Instead, they push the feature branch to the central server and file a **pull request** asking to merge their additions into master. This gives other developers an opportunity to **review** the changes before they become a part of the main codebase.

Before you start developing a feature, you need an isolated branch to work on. You can request a new branch with the following command:

```
git checkout -b marys-feature master
```

This checks out a branch called _marys-feature_ based on master, and the **-b** flag tells Git to create the branch if it doesn’t already exist. On this branch, you edit, stage, and commit changes in the usual fashion, building up your feature with as many commits as necessary:

```
git status
git add <some-file>
git commit
```

If you were collaborating with other developers, the command below would also give them access to your initial commits.

```
git push -u origin marys-feature
```

This command pushes _marys-feature_ to the central repository (origin), and the **-u** flag adds it as a remote tracking branch. After setting up the tracking branch, you can call git push without any parameters to push your feature i.e. `git push`.

### Merging your feature branch into master branch

After you are finished implementing your feature, you file a **pull request** in your Git GUI asking to merge the _marys-feature_ branch into _master_ branch and team members will be notified automatically. Once your changes are accepted, the code merge into the master(stable) branch goes like this:

```
git checkout master
git pull
git pull origin marys-feature
git push
```

First, whoever’s performing the merge needs to check out their master branch and make sure it’s **up to date**. Then, _git pull origin marys-feature_ **merges** the central repository’s copy of _marys-feature_. You could also use a simple `git merge marys-feature`, but the command shown above makes sure you’re always pulling the most **up-to-date** version of the feature branch. Finally, the updated master needs to get **pushed back** to origin

###### _Credits: https://www.atlassian.com/git/tutorials/comparing-workflows/centralized-workflow_
