import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  title: "Umapyoi Mod",
  description: "以《赛马娘 Pretty Derby》为主题的“玩家育成”模组",
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: '首页', link: '/' },
    ],

    socialLinks: [
      { icon: 'github', link: 'https://github.com/0999312/umapyoi' }
    ]
  }
})
