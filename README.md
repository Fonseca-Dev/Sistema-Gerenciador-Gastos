# 📱 React Mobile App

Um projeto React com TypeScript otimizado para dimensões móveis, desenvolvido para executar no navegador web com layout responsivo que simula um dispositivo móvel.

## 🚀 Tecnologias

- **React 18** - Biblioteca para interfaces de usuário
- **TypeScript** - Superset tipado do JavaScript
- **Vite** - Build tool ultrarrápida
- **CSS3** - Estilos modernos e responsivos

## 📏 Especificações

### Dimensões Mobile
- **Largura**: 375px (padrão iPhone)
- **Altura mínima**: 812px (padrão iPhone X)
- **Viewport**: Otimizado para dispositivos móveis
- **Responsivo**: Adapta-se automaticamente a telas menores que 420px

### Características do Layout
- Container centralizado na tela
- Box shadow para simular dispositivo físico
- Scrolling vertical quando necessário
- Transições suaves e animações touch-friendly

## 🛠️ Instalação e Execução

### Pré-requisitos
- Node.js 18+ 
- npm ou yarn

### Comandos

```bash
# Instalar dependências
npm install

# Executar em modo desenvolvimento
npm run dev
# Acesse: http://localhost:5173/

# Build para produção
npm run build

# Preview da build de produção
npm run preview
# Acesse: http://localhost:4173/

# Linting do código
npm run lint
```

## 📁 Estrutura do Projeto

```
src/
├── components/
│   ├── Layout.tsx          # Container principal com padding responsivo
│   └── MobileCard.tsx      # Componente de card reutilizável
├── App.tsx                 # Componente principal da aplicação
├── App.css                 # Estilos específicos do App
├── index.css               # Estilos globais e configurações mobile
└── main.tsx                # Ponto de entrada da aplicação
```

## 🎨 Componentes

### Layout
Componente wrapper que fornece padding consistente e estrutura responsiva.

```tsx
<Layout className="flex flex-col items-center">
  {/* Conteúdo */}
</Layout>
```

### MobileCard
Componente de card com diferentes variantes visuais.

```tsx
<MobileCard title="Título" variant="primary">
  {/* Conteúdo do card */}
</MobileCard>
```

**Variantes disponíveis:**
- `default` - Estilo padrão cinza
- `primary` - Gradiente azul
- `secondary` - Gradiente vermelho

## 🎯 Características Específicas para Mobile

### CSS Reset e Base
- Box-sizing border-box global
- Remoção de margens/padding padrão
- Font-size otimizado para mobile (16px base)

### Utilitários CSS
Classes utilitárias incluídas:
- `.flex`, `.flex-col` - Flexbox layouts
- `.items-center`, `.justify-center` - Alinhamento
- `.text-center` - Alinhamento de texto
- `.mb-4`, `.mt-4` - Margins responsivos

### Responsividade
Media query para telas menores que 420px:
- Container ocupa 100% da viewport
- Remove box-shadow
- Ajusta padding interno
- Reduz tamanhos de fonte

## 🌐 Deploy

O projeto pode ser facilmente deployado em qualquer plataforma de hospedagem estática:

### Netlify
```bash
npm run build
# Faça upload da pasta dist/
```

### Vercel
```bash
npm run build
# Conecte seu repositório GitHub ao Vercel
```

### GitHub Pages
```bash
npm run build
# Configure GitHub Pages para usar a pasta dist/
```

## 📱 Teste em Dispositivos

### Desktop
O projeto simula perfeitamente um dispositivo móvel em navegadores desktop, centralizando o container e aplicando sombras para visual de dispositivo físico.

### Mobile Real
Em dispositivos móveis reais, o container se adapta automaticamente para ocupar toda a tela disponível, mantendo a experiência otimizada.

### DevTools
Use as ferramentas de desenvolvedor do navegador:
1. F12 para abrir DevTools
2. Clique no ícone de dispositivo móvel
3. Selecione "iPhone X" ou similar para testar

## 🔧 Personalização

### Alterar Dimensões
Edite o arquivo `src/index.css`:

```css
#root {
  width: 375px;    /* Largura do container */
  min-height: 812px; /* Altura mínima */
}
```

### Breakpoints Responsivos
Modifique a media query:

```css
@media (max-width: 420px) {
  /* Ajustes para telas menores */
}
```

### Cores e Temas
Personalize as variáveis CSS em `:root`:

```css
:root {
  --primary-color: #646cff;
  --secondary-color: #ff6b6b;
  --background: #242424;
}
```

---

Desenvolvido com ❤️ usando React + TypeScript + Vite

      // Remove tseslint.configs.recommended and replace with this
      tseslint.configs.recommendedTypeChecked,
      // Alternatively, use this for stricter rules
      tseslint.configs.strictTypeChecked,
      // Optionally, add this for stylistic rules
      tseslint.configs.stylisticTypeChecked,

      // Other configs...
    ],
    languageOptions: {
      parserOptions: {
        project: ['./tsconfig.node.json', './tsconfig.app.json'],
        tsconfigRootDir: import.meta.dirname,
      },
      // other options...
    },
  },
])
```

You can also install [eslint-plugin-react-x](https://github.com/Rel1cx/eslint-react/tree/main/packages/plugins/eslint-plugin-react-x) and [eslint-plugin-react-dom](https://github.com/Rel1cx/eslint-react/tree/main/packages/plugins/eslint-plugin-react-dom) for React-specific lint rules:

```js
// eslint.config.js
import reactX from 'eslint-plugin-react-x'
import reactDom from 'eslint-plugin-react-dom'

export default defineConfig([
  globalIgnores(['dist']),
  {
    files: ['**/*.{ts,tsx}'],
    extends: [
      // Other configs...
      // Enable lint rules for React
      reactX.configs['recommended-typescript'],
      // Enable lint rules for React DOM
      reactDom.configs.recommended,
    ],
    languageOptions: {
      parserOptions: {
        project: ['./tsconfig.node.json', './tsconfig.app.json'],
        tsconfigRootDir: import.meta.dirname,
      },
      // other options...
    },
  },
])
```
