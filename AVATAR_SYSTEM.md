# Sistema de Avatar do Usuário

## Como funciona

O sistema de avatar do usuário utiliza o `localStorage` do navegador para manter a foto e o nome do usuário em todo o contexto da aplicação.

## Implementação

### 1. Cadastro (Signup.tsx)
- O usuário pode fazer upload de uma foto durante o cadastro
- A foto é convertida para base64 e salva no `localStorage` com a chave `userAvatar`
- O nome do usuário é salvo no `localStorage` com a chave `userName`

### 2. Login (Login.tsx)
- Recupera a foto do `localStorage` e exibe no componente
- Se não houver foto, mostra um ícone padrão

### 3. Home (Home.tsx)
- Recupera tanto a foto quanto o nome do `localStorage`
- Exibe a foto no botão de perfil
- Personaliza a saudação com o nome do usuário
- Escuta mudanças no `localStorage` para atualizar em tempo real

## Chaves do localStorage

- `userAvatar`: Imagem em formato base64
- `userName`: Nome do usuário como string

## Como usar em outros componentes

```tsx
import React, { useState, useEffect } from "react";

const MeuComponente: React.FC = () => {
  const [userAvatar, setUserAvatar] = useState<string | null>(() => {
    return localStorage.getItem('userAvatar') || null;
  });
  
  const [userName, setUserName] = useState<string>(() => {
    return localStorage.getItem('userName') || 'Usuário';
  });

  // Opcional: escutar mudanças no localStorage
  useEffect(() => {
    const handleStorageChange = (e: StorageEvent) => {
      if (e.key === 'userAvatar') {
        setUserAvatar(e.newValue);
      }
      if (e.key === 'userName') {
        setUserName(e.newValue || 'Usuário');
      }
    };

    window.addEventListener('storage', handleStorageChange);
    return () => window.removeEventListener('storage', handleStorageChange);
  }, []);

  return (
    <div>
      {userAvatar && (
        <img 
          src={userAvatar} 
          alt="Avatar" 
          style={{
            width: '50px',
            height: '50px',
            borderRadius: '50%',
            objectFit: 'cover'
          }}
        />
      )}
      <p>Olá, {userName}!</p>
    </div>
  );
};
```

## Vantagens

1. **Persistência**: Os dados são mantidos mesmo após fechar e abrir o navegador
2. **Simplicidade**: Não requer backend ou contexto React complexo
3. **Performance**: Acesso rápido aos dados do usuário
4. **Compatibilidade**: Funciona em todos os navegadores modernos

## Considerações

- Os dados ficam salvos apenas no navegador local
- Para aplicações em produção, considere sincronizar com um backend
- O localStorage tem limite de armazenamento (geralmente 5-10MB)